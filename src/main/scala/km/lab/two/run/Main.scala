package km.lab.two.run

import km.lab.two.timeslot.Timeslot
import km.lab.two.detail.{DetailType, Detail}
import scala.collection.mutable

/**
 * @author phpusr
 *         Date: 08.05.14
 *         Time: 14:06
 */

/**
 * TODO
 */
object Main extends App {

  val detailQueue = mutable.Queue[Detail]()

  val incomingIntervalTypeOne = Timeslot(15, 5)
  var detailsCount = 0
  val detailName = "Detail One"
  while(true) {
    detailsCount += 1
    Thread.sleep(incomingIntervalTypeOne.get)
    val detail = Detail(detailName + detailsCount, DetailType.V1)
    detailQueue += detail
  }

  //val incomingIntervalTypeTwo = Timeslot(35, 8)

  // Размещение деталей по станкам
  while(detailQueue.nonEmpty) {
    val detail = detailQueue.dequeue()
    val currentOperation = detail.currentOperation

    // Если есть следующая операция, то помещаем деталь в очередь станка, выполняющего эту операцию
    if (currentOperation.isDefined) currentOperation.get.machineTool.addDetail(detail)
    // Если нет, значти деталь обработана полностью, отправляем ее на склад
    else ??? //TODO на склад
  }
}
