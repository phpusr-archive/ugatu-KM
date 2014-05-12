package km.lab.two.detail

import km.lab.two.timeslot.Timeslot
import org.dyndns.phpusr.util.log.Logger
import java.util.concurrent.atomic.AtomicBoolean

/**
 * @author phpusr
 *         Date: 08.05.14
 *         Time: 14:35
 */

/**
 * Генератор деталей
 */
class DetailGenerator(detailType: DetailType, action: (Detail, Boolean) => Unit, incomingInterval: Timeslot) {

  /** Флаг работы генератора */
  private val enable = new AtomicBoolean(false)

  /** Logger */
  private val logger = new Logger(true, true, true)

  /** Поток для генерации деталей */
  private val generator = new Thread(new Runnable {
    override def run() {
      val detailName = "Detail"
      var detailIndex = 0
      while (enable.get) {
        detailIndex += 1
        val incomingMilis = incomingInterval.get
        logger.debug(s"Incoming interval: ${incomingMilis/1000} s.")

        Thread.sleep(incomingMilis)
        val detail = Detail(s"$detailName-$detailIndex", detailType)
        logger.debug(s"DetailGenerator generate detail: $detail")

        action(detail, true)
      }
    }
  })

  /** Генерация деталей */
  def start() {
    logger.debug("DetailGenerator start")
    enable.set(true)
    generator.start()
  }

  /** Остновка генерации деталей */
  def stop() {
    enable.set(false)
  }

}
