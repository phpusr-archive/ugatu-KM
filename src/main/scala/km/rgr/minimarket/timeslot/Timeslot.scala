package km.rgr.minimarket.timeslot

import scala.util.Random
import km.rgr.minimarket.constants.Const

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
  private def acceleration = Const.Acceleration

  /** Интервал в милисекундах */
  def get = {
    val delta = Random.nextInt(deltaMinutes+1)
    val interval = defaultMinutes + delta
    val milis = interval.toLong * 60 * 1000 / acceleration
    milis
  }
}

object Timeslot {
  def apply(defaultMinutes: Int, deltaMinutes: Int) = new Timeslot(defaultMinutes, deltaMinutes)
}
