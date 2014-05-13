package km.lab.two.detail

import scala.collection.mutable
import java.util.Date

/**
 * @author phpusr
 *         Date: 08.05.14
 *         Time: 13:03
 */

/**
 * Тип детали
 */
case class DetailType(name: String, operations: Seq[Operation])

/**
 * Типы деталей
 */
object DetailType {
  import km.lab.two.detail.{Operation => Op}

  /** Деталь 1-о типа */
  val V1 = DetailType("One", Seq(Op._1, Op._2, Op._3))
  /** Деталь 2-о типа */
  val V2 = DetailType("Two", Seq(Op._4, Op._5, Op._6))
}

/**
 * Деталь
 */
case class Detail(name: String, detailType: DetailType) {
  /** Очередь операций над деталью */
  private val operationQueue = mutable.Queue[Operation]()

  /** Дата создания детали */
  private val createDate = new Date()

  /** Время обработки (мс.) */
  var handlerTime = 0L

  //---------------------- Init ----------------------//
  detailType.operations.foreach(operationQueue +=)
  //---------------------- End init ------------------//

  /** Выполнить операцию над деталью из очереди */
  def operation(action: Unit => Unit) {
    val op = operationQueue.dequeue()
    val handlingTime = op.handlingTime.get
    Thread.sleep(handlingTime)
    action()
  }

  /** Текущая операция */
  def currentOperation = if (operationQueue.nonEmpty) Option(operationQueue.head) else None

  /** Подсчитать время обработки */
  def calcHandlerTime() = handlerTime = new Date().getTime - createDate.getTime

  override def toString = s"$name type ${detailType.name}"
}
