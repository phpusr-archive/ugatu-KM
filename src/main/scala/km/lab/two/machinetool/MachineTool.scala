package km.lab.two.machinetool

import scala.collection.mutable
import km.lab.two.detail.Detail
import org.dyndns.phpusr.util.log.Logger

/**
 * @author phpusr
 *         Date: 08.05.14
 *         Time: 12:46
 */

/**
 * Станок
 */
case class MachineTool(name: String) {
  /** Включен или выключен станок */
  private var enable = false

  /** Очередь деталей */
  private val detailQueue = mutable.Queue[Detail]()

  /** Действие над обработанной деталью */
  private var action: Detail => Unit = null

  /** Logger */
  val logger = new Logger(true, true, true)

  /** Обработчик деталей */
  private val handler = new Thread(new Runnable {
    override def run() {
      while (enable) {
        detailQueue.synchronized {
          if (detailQueue.nonEmpty) {
            val currentDetail = detailQueue.dequeue()
            logger.debug(s"${MachineTool.this} processes $currentDetail")
            currentDetail.operation()
            action(currentDetail)
          }
        }
        Thread.sleep(500)
      }
    }
  })

  //--------------------------------------------------

  /** //TODO */
  //def act_= (a: Detail => Unit): Unit = action = a

  /** Добавление детали в очередь */
  def addDetail(detail: Detail) = synchronized {
    logger.debug(s"$this add $detail")
    detailQueue += detail
  }

  /** Запуск станка */
  def start() {
    logger.debug(s"Start $this")
    enable = true
    handler.start()
  }

  /** Остановка станка */
  def stop() {
    logger.debug(s"Stop $this")
    enable = false
  }

}

/**
 * Станки участка цеха
 */
object MachineTool {
  val A1 = MachineTool("A1")
  val A2 = MachineTool("A2")
  val A3 = MachineTool("A3")
  
  private val list = List(A1, A2, A3)

  /** Установить действие над обработанной деталью всем станкам */
  def setAction(action: Detail => Unit) = list.foreach(_.action = action)

  /** Запустить все станки */
  def startAll() = list.foreach(_.start())

  /** Остановить все станки */
  def stopAll() = list.foreach(_.stop())

  /** Размеры очередей на станках */
  def detailQueueSize = list.map(_.detailQueue.size)
}
