package km.rgr.minimarket

import java.util.{TimerTask, Timer}
import km.rgr.minimarket.constants.Const

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
  private val shoppingInterval = Const.CustomerShoppingInterval.get
  
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
