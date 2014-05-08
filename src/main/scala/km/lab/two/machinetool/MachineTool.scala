package km.lab.two.machinetool

import km.lab.two.detail.{GlobalQueue, Detail}
import scala.collection.mutable

/**
 * @author phpusr
 *         Date: 08.05.14
 *         Time: 12:46
 */

/**
 * Станок
 */
case class MachineTool(name: String, globalDetailQueue: GlobalQueue) {
  /** Включен или выключен станок */
  private val enable = false

  /** Очередь деталей */
  private val detailQueue = mutable.Queue[Detail]()

  /** Добавление детали в очередь */
  def addDetail(detail: Detail) {
    detailQueue += detail
  }

  /** Обработка деталей */
  private def treatmentDetails() {
    while (enable) {
      if (!detailQueue.isEmpty) {
        val currentDetail = detailQueue.dequeue()
        currentDetail.operation()

        // Возвращаение детали в глобаольную очередь
        globalDetailQueue += currentDetail
      }
    }
  }
}

/**
 * Станки участка цеха
 */
object MachineTool {
  val globalQueue = new GlobalQueue //TODO возможно нужно будет перенести

  val A1 = MachineTool("A1", globalQueue)
  val A2 = MachineTool("A2", globalQueue)
  val A3 = MachineTool("A3", globalQueue)
}
