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
  private val customerGenerator = new CustomerGenerator({ c =>
    customerCount.getAndAdd(1)
    synchronized(notServiceCustomerList += c)
  })

  /** Статистика */
  private val stat = MiniMarketStat(new Stat)

  /** Поток обслуживания очереди покупателей */
  private val serviceThread = new Thread(new Runnable {
    override def run() {
      while(enable.get) {
        if (cashier.free && queue.nonEmpty) {
          val customer = queue.dequeue()
          cashier.serviceCustomer(customer)
          serviceCustomerList += customer
        }
      }
    }
  })

  /** Поток становления покупателей в очередь */
  private val queueThread = new Thread(new Runnable {
    override def run() {
      while(enable.get) {
        synchronized {
          notServiceCustomerList.filter(_.allBought).foreach { c =>
            queue += c
            notServiceCustomerList -= c
          }
        }        
        Thread.sleep(Const.ThreadSleepMilis)
      }
    }
  })

  /** Таймер подсчета статистики */
  private val statTimer = new Timer(1000, new ActionListener {
    override def actionPerformed(e: ActionEvent) = synchronized {
      stat.queueLength.newElementAndAdd(queue.size)
    }
  })

  /** Запуск работы магазина */
  def start() {
    logger.debug("MiniMarket start")

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
  case class MiniMarketInfo(customerCount: Int, serviceCustomerCount: Int, notServiceCustomerCount: Int, customerServiceNowCount: Int, queueLength: Int, avgQueueLength: Float)

  /** Информация о работе магазина */
  def getInfo = synchronized {
    MiniMarketInfo(customerCount.get, serviceCustomerList.size, notServiceCustomerList.size, cashier.customerServiceNowCount, queue.size, stat.queueLength.avg)
  }

  /** Остановка генерирования покупателей */
  def stopCustomerGenerator() = customerGenerator.stop()

}
