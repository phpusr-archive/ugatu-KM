package km.rgr.minimarket

import org.dyndns.phpusr.util.queue.MultiThreadQueue
import java.util.concurrent.atomic.{AtomicInteger, AtomicBoolean}
import scala.collection.mutable.ListBuffer
import km.rgr.minimarket.constants.Const
import org.dyndns.phpusr.util.log.Logger
import km.rgr.minimarket.stat.MiniMarketStat
import org.dyndns.phpusr.util.stat.Stat
import javax.swing.Timer
import java.awt.event.{ActionEvent, ActionListener}
import java.util.Date

/**
 * @author phpusr
 *         Date: 15.05.14
 *         Time: 13:52
 */

/**
 * Мини-маркет
 */
class MiniMarket {

  private val logger = new Logger(true, true, true)

  /** Очередь покупателей */
  private val queue = new MultiThreadQueue[Customer]

  /** Работает-ли магазин */
  private val enable = new AtomicBoolean(false)

  /** Кассир */
  private val cashier = Cashier()

  /** Не обслуженные покупатели */
  private val notServiceCustomerList = ListBuffer[Customer]()

  /** Обслуженные покупатели */
  private val serviceCustomerList = ListBuffer[Customer]()

  /** Счетчик зашедших покупателей */
  private val customerCount = new AtomicInteger(0)
  /** Генератор покупателей */
  private val customerGenerator = new CustomerGenerator({ customer =>
    customerCount.getAndAdd(1)
    // Установка времени посещения
    customer.setVisitTime()
    synchronized(notServiceCustomerList += customer)
  })

  /** Статистика */
  private val stat = MiniMarketStat(new Stat, new Stat, new Stat, new Stat, new Stat)

  /** Время начала работы */
  private var startTime: Date = null

  /** Поток становления покупателей в очередь */
  private val queueThread = new Thread(new Runnable {
    override def run() {
      while(enable.get) {
        synchronized {
          notServiceCustomerList.filter(_.allBought).foreach { customer =>
          // Старт подсчета времени ожидания
            customer.startWait()
            queue += customer
            notServiceCustomerList -= customer
          }
        }
        Thread.sleep(Const.ThreadSleepMilis)
      }
    }
  })

  /** Поток обслуживания очереди покупателей */
  private val serviceThread = new Thread(new Runnable {
    override def run() {
      while(enable.get) {
        if (cashier.free && queue.nonEmpty) {
          val customer = queue.dequeue()
          // Подсчет среденго времени ожидания обслуживания
          stat.waitServiceTime.newElementAndAdd(customer.calcWait())
          cashier.serviceCustomer(customer)
          // Подсчет среденго времени пребывания покупателей
          stat.stayTime.newElementAndAdd(customer.stayTime)
          serviceCustomerList += customer
        }
      }
    }
  })

  /** Таймер подсчета статистики */
  private val statTimer = new Timer(1000, new ActionListener {
    override def actionPerformed(e: ActionEvent) = synchronized {
      stat.queueLength.newElementAndAdd(queue.size)
      stat.cashierUpTime.newElementAndAdd(cashier.customerServiceNowCount)
      stat.customerCount.newElementAndAdd(cashier.customerServiceNowCount + notServiceCustomerList.size)
    }
  })

  /** Время работы (мс.) */
  def workTime = new Date().getTime - startTime.getTime

  /** Запуск работы магазина */
  def start() {
    logger.debug("MiniMarket start")

    startTime = new Date()
    enable.set(true)
    // Запуск подсчета статистики
    statTimer.start()
    // Запуск обслуживания
    serviceThread.start()
    // Запуск становления покупателей в очередь
    queueThread.start()
    // Запуск генератора покупателей
    customerGenerator.start()
  }

  /** Остановка работы магазина */
  def stop() {
    logger.debug("MiniMarket stop")

    enable.set(false)
    customerGenerator.stop()
    statTimer.stop()
  }

  /** Хранилище информации о магазине */
  case class MiniMarketInfo(customerCount: Int, serviceCustomerCount: Int, notServiceCustomerCount: Int, customerServiceNowCount: Int, queueLength: Int,
                            pCashierDownTime: Float, avgQueueLength: Float, avgCustomerCount: Float, avgWaitServiceTime: Float, avgStayTime: Float)

  /** Информация о работе магазина */
  def getInfo = synchronized {
    MiniMarketInfo(customerCount.get, serviceCustomerList.size, notServiceCustomerList.size, cashier.customerServiceNowCount, queue.size,
      1-stat.cashierUpTime.avg, stat.queueLength.avg, stat.customerCount.avg, stat.waitServiceTime.avg, stat.stayTime.avg)
  }

  /** Остановка генерирования покупателей */
  def stopCustomerGenerator() = customerGenerator.stop()

}
