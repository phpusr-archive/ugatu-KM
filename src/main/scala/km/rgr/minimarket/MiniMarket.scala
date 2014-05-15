package km.rgr.minimarket

import org.dyndns.phpusr.util.queue.MultiThreadQueue
import java.util.concurrent.atomic.AtomicBoolean
import scala.collection.mutable.ListBuffer
import km.rgr.minimarket.constants.Const
import org.dyndns.phpusr.util.log.Logger

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

  /** Покупатели находящиеся в магазине */
  private val customerList = ListBuffer[Customer]()

  /** Генератор покупателей */
  private val customerGenerator = new CustomerGenerator({ c =>
    synchronized(customerList += c)
  })

  /** Поток обслуживания очереди покупателей */
  private val serviceThread = new Thread(new Runnable {
    override def run() {
      while(enable.get) {
        if (cashier.free && queue.nonEmpty) {
          val customer = queue.dequeue()
          cashier.serviceCustomer(customer)
        }
      }
    }
  })

  /** Поток становления покупателей в очередь */
  private val queueThread = new Thread(new Runnable {
    override def run() {
      while(enable.get) {
        synchronized {
          customerList.filter(_.allBought).foreach(queue += _)
        }
        Thread.sleep(Const.ThreadSleepMilis)
      }
    }
  })

  /** Запуск работы магазина */
  def start() {
    logger.debug("MiniMarket start")

    enable.set(true)
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
  }


}
