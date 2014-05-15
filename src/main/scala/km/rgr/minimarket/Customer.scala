package km.rgr.minimarket

import km.rgr.minimarket.timeslot.Timeslot
import java.util.{TimerTask, Timer}

/**
 * @author phpusr
 *         Date: 15.05.14
 *         Time: 13:51
 */

/**
 * Покупатель
 */
case class Customer(name: String) {

  /** Интервал времени покупок покупателя */
  private val shoppingInterval = Timeslot(5, 90).get
  
  private var _allBought = false

  // Таймер покупок покупателя
  new Timer().schedule(new TimerTask {
    override def run() {
      _allBought = true 
    }
  }, shoppingInterval)

  /** Все ли купил покупатель (идет в очередь) */
  def allBought = _allBought

}
