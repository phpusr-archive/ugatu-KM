package km.rgr.minimarket.constants

import km.rgr.minimarket.timeslot.Timeslot

/**
 * @author phpusr
 *         Date: 12.05.14
 *         Time: 13:28
 */

/**
 * Константы
 */
object Const {

  /** Ускорение времени */
  val Acceleration = 600

  /** Время засыпания потоков */
  val ThreadSleepMilis = 100

  //--------------------------------------------------//

  /** Интервал времени покупок покупателя */
  val CustomerShoppingInterval = Timeslot(5, 90)

  /** Интервал времени обслуживания */
  val CashierServiceTime = Timeslot(1, 5)

  /** Перерыв между заходами покупателей */
  val CustomerGenerationTimeout = Timeslot(2, 5)

}
