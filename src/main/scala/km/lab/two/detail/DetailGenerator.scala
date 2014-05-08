package km.lab.two.detail

import km.lab.two.timeslot.Timeslot

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

  /** Поток для генерации деталей */
  private val generator = new Thread(new Runnable {
    override def run() = {
      val detailName = "Detail"
      var detailIndex = 0
      while (work) {
        detailIndex += 1
        Thread.sleep(incomingInterval.get)
        val detail = Detail(s"$detailName-$detailIndex", DetailType.V1)

        action(detail)
      }
    }
  })

  /** Генерация деталей */
  def start() {
    work = true
    generator.start()
  }

  /** Остновка генерации деталей */
  def stop() {
    work = false
  }

}
