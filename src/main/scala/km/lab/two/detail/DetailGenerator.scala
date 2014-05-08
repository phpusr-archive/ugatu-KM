package km.lab.two.detail

import km.lab.two.timeslot.Timeslot
import org.dyndns.phpusr.util.log.Logger

/**
 * @author phpusr
 *         Date: 08.05.14
 *         Time: 14:35
 */

/**
 * Генератор деталей
 */
class DetailGenerator(detailType: DetailType, action: Detail => Unit, incomingInterval: Timeslot) {

  /** Флаг работы генератора */
  private var work = false

  /** Logger */
  val logger = new Logger(true, true, true)

  /** Поток для генерации деталей */
  private val generator = new Thread(new Runnable {
    override def run() {
      val detailName = "Detail"
      var detailIndex = 0
      while (work) {
        detailIndex += 1
        val incomingMilis = incomingInterval.get
        logger.debug(s"Incoming interval: ${incomingMilis/1000} s.")

        Thread.sleep(incomingMilis)
        val detail = Detail(s"$detailName-$detailIndex", DetailType.V1)
        logger.debug(s"DetailGenerator generate detail: $detail")

        action(detail)
      }
    }
  })

  /** Генерация деталей */
  def start() {
    logger.debug("DetailGenerator start")
    work = true
    generator.start()
  }

  /** Остновка генерации деталей */
  def stop() {
    work = false
  }

}
