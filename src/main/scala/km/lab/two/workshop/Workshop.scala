package km.lab.two.workshop

import km.lab.two.timeslot.Timeslot
import km.lab.two.detail.{DetailGenerator, DetailType, Detail}
import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import org.dyndns.phpusr.util.log.Logger
import km.lab.two.machinetool.MachineTool
import java.util.concurrent.atomic.{AtomicBoolean, AtomicInteger}
import java.awt.event.ActionListener
import java.util.{Date, TimerTask, Timer}
import km.lab.two.constants.Const

/**
 * @author phpusr
 *         Date: 08.05.14
 *         Time: 14:06
 */

/**
 * Мастерская
 */
class Workshop(workHours: Int) extends Thread {

  /** Состояние работы мастерской */
  val enable = new AtomicBoolean(true)

  /** Очередь необработанных деталей */
  val detailQueue = mutable.Queue[Detail]()

  /** Склад обработанных деталей */
  val warehouse = ListBuffer[Detail]()

  /** Logger */
  private val logger = new Logger(true, true, false)

  /** Всего сгенерировано деталей */
  val generateDetailCount = new AtomicInteger(0)

  /** Добавление детали в очередь */
  def addDetailToQueue(detail: Detail, isGenerate: Boolean): Unit = synchronized {
    logger.debug(s"Add detail to queue: $detail")
    if (isGenerate) generateDetailCount.getAndAdd(1)
    detailQueue += detail
  }

  // Поток деталей 1-го типа
  private val incomingIntervalTypeOne = Timeslot(15, 5)
  private val generatorV1 = new DetailGenerator(DetailType.V1, addDetailToQueue, incomingIntervalTypeOne)
  // Поток деталей 2-го типа
  private val incomingIntervalTypeTwo = Timeslot(35, 8)
  private val generatorV2 = new DetailGenerator(DetailType.V2, addDetailToQueue, incomingIntervalTypeTwo)

  /** Время начало работы */
  private var startDate: Date = null

  /** Отработанное время (мс.) */
  def workedTime = new Date().getTime - startDate.getTime

  /** Запуск поставки и обработки деталей */
  override def run() {
    startDate = new Date()

    // Запуск поставки деталей
    generatorV1.start()
    generatorV2.start()

    // Запуск станков
    MachineTool.setAction(addDetailToQueue)
    MachineTool.startAll()

    // Размещение деталей по станкам
    while(enable.get) {
      detailQueue.synchronized {
        if (detailQueue.nonEmpty) {
          val detail = detailQueue.dequeue()
          val currentOperation = detail.currentOperation

          // Если есть следующая операция, то помещаем деталь в очередь станка, выполняющего эту операцию
          if (currentOperation.isDefined) {
            val machineTool = currentOperation.get.machineTool
            logger.debug(s"Detail: $detail add to $machineTool")
            machineTool.addDetail(detail)
          }
          // Если нет, значит деталь обработана полностью, отправляем ее на склад
          else addToWarehouse(detail)
        }
      }
      Thread.sleep(500)
    }
  }

  // Время работы мастерской
  private val endDate = new Date(new Date().getTime + (workHours * 60 * 60 * 1000 / Const.Acceleration))
  new Timer().schedule(new TimerTask {
    override def run() {
      logger.debug("Switch off workshop")
      stopGenerateDetail()
      MachineTool.stopAll()
      enable.set(false)
    }
  }, endDate)

  /** Остановка поставки деталей */
  def stopGenerateDetail() {
    generatorV1.stop()
    generatorV2.stop()
  }

  /** Добавить деталь на склад */
  private def addToWarehouse(detail: Detail) {
    logger.debug(s"Add detail to warehouse: $detail")
    detail.calcHandlerTime()
    warehouse += detail
  }

  /** Размеры очередей на станках */
  def machineToolDetailQueueSize = MachineTool.detailQueueSize

  /** Среднее время обработки деталей каждого типа (мс.) */
  def avgDetailHandlerTime = {
    val v1 = avgDetailHandlerTimeByType(DetailType.V1)
    val v2 = avgDetailHandlerTimeByType(DetailType.V2)
    (v1.toLong, v2.toLong)
  }

  /** Среднее время обработки деталей по типу (мс.) */
  private def avgDetailHandlerTimeByType(detailType: DetailType) = {
    val handlerTimes = warehouse.filter(_.detailType == detailType).map(_.handlerTime)
    handlerTimes.sum.toDouble / handlerTimes.size
  }

}
