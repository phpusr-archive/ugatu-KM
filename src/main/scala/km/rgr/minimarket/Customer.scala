package km.rgr.minimarket

import java.util.{Date, TimerTask, Timer}
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
  private val ShoppingInterval = Const.CustomerShoppingInterval.get

  /** Все ли купил покупатель (идет в очередь) */
  private var _allBought = false

  /** Время становления в очередь */
  private var _startWaitTime: Date = null

  /** Время ожидания обслуживания (мс.) */
  private var _waitMillis = 0L

  // Таймер покупок покупателя
  new Timer().schedule(new TimerTask {
    override def run() {
      _allBought = true 
    }
  }, ShoppingInterval)

  /** Все ли купил покупатель (идет в очередь) */
  def allBought = _allBought

  /** Покупатель встал в очередь ожидания обслуживания */
  def startWait() = _startWaitTime = new Date

  /** Подсчитать время ожидания обслуживания */
  def calcWait() = {
    assert(_startWaitTime != null)
    _waitMillis = new Date().getTime - _startWaitTime.getTime
    waitMillis
  }

  /** Время ожидания обслуживания (мс.) */
  def waitMillis = {
    assert(_startWaitTime != null)
    //assert(_waitMillis != 0) //TODO Иногда возникает ошибка
    _waitMillis
  }


}
