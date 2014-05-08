package km.lab.two.machinetool

import km.lab.two.detail.Detail
import scala.collection.mutable

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
  private val enable = false

  /** Глобальная очередь деталей */
  private val globalDetailQueue: Option[mutable.Queue[Detail]] = None

  /** Очередь деталей */
  private val detailQueue = mutable.Queue[Detail]()

  //--------------------------------------------------

  /** Добавление детали в очередь */
  def addDetail(detail: Detail) {
    detailQueue += detail
  }

  /** Обработка деталей */
  //TODO в отдельном потоке
  private def treatmentDetails() {
    while (enable) {
      if (!detailQueue.isEmpty) {
        val currentDetail = detailQueue.dequeue()
        currentDetail.operation()

        // Возвращаение детали в глобаольную очередь
        globalDetailQueue.get += currentDetail
      }
    }
  }
}

/**
 * Станки участка цеха
 */
object MachineTool {
  val A1 = MachineTool("A1")
  val A2 = MachineTool("A2")
  val A3 = MachineTool("A3")
}
