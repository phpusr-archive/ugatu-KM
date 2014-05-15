package km.rgr.minimarket

import km.rgr.minimarket.constants.Const
import java.util.concurrent.atomic.AtomicBoolean

/**
 * @author phpusr
 *         Date: 15.05.14
 *         Time: 13:52
 */

/**
 * Кассир
 */
case class Cashier() {

  /** Свободен или занят */
  private val _free = new AtomicBoolean(true)

  /** Интервал времени обслуживания */
  private val serviceTime = Const.CashierServiceTime

  /** Свободен ли кассир */
  def free: Boolean = _free.get

  /** Обслужить покупателя */
  def serviceCustomer(customer: Customer) = synchronized {
    assert(free)

    _free.set(false)
    Thread.sleep(serviceTime.get)
    _free.set(true)
  }

  /** Количество обслуживаемых покупателей в данный момент */
  def customerServiceNowCount = if (free) 0 else 1

}
