package km.lab.two.run

import km.lab.two.timeslot.Timeslot
import km.lab.two.detail.{DetailGenerator, DetailType, Detail}
import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
 * @author phpusr
 *         Date: 08.05.14
 *         Time: 14:06
 */

/**
 * TODO
 */
object Main extends App {

  /** Очередь необработанных деталей */
  val detailQueue = mutable.Queue[Detail]()

  /** Склад обработанных деталей */
  val wareHouse = ListBuffer[Detail]()

  /** Добавление детали в очередь */
  val addDetailToQueue = (detail: Detail) => {
    detailQueue += detail
    print() // Для того, чтобы тип был Unit
  }

  // Поток деталей 1-го типа
  val incomingIntervalTypeOne = Timeslot(15, 5)
  val generatorV1 = new DetailGenerator(DetailType.V1, addDetailToQueue, incomingIntervalTypeOne)

  // Поток деталей 2-го типа
  val incomingIntervalTypeTwo = Timeslot(35, 8)
  val generatorV2 = new DetailGenerator(DetailType.V2, addDetailToQueue, incomingIntervalTypeTwo)

  // Размещение деталей по станкам
  while(detailQueue.nonEmpty) {
    val detail = detailQueue.dequeue()
    val currentOperation = detail.currentOperation

    // Если есть следующая операция, то помещаем деталь в очередь станка, выполняющего эту операцию
    if (currentOperation.isDefined) currentOperation.get.machineTool.addDetail(detail)
    // Если нет, значти деталь обработана полностью, отправляем ее на склад
    else addToWarehouse(detail)
  }

  /** Добавить деталь на склад */
  def addToWarehouse(detail: Detail) = wareHouse += detail

}
