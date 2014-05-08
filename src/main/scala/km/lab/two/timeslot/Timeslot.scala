package km.lab.two.timeslot

import scala.util.Random
import km.lab.two.run.Main

/**
 * @author phpusr
 *         Date: 08.05.14
 *         Time: 12:55
 */

/**
 * Временной интервал
 */
class Timeslot(defaultMinutes: Int, deltaMinutes: Int) {

  /** Ускорение времени */
  private def acceleration = Main.Acceleration

  /** Интервал в милисекундах */
  def get = {
    val delta = Random.nextInt(deltaMinutes+1)
    val interval = defaultMinutes + delta
    val milis = interval.toLong * 60 * 1000 / acceleration
    milis
  }
}
