package km.rgr.minimarket

import km.rgr.minimarket.constants.Const

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
  private var _free = true

  /** Интервал времени обслуживания */
  private val serviceTime = Const.CashierServiceTime

  /** Свободен ли кассир */
  def free: Boolean = _free

  /** Обслужить покупателя */
  def serviceCustomer(customer: Customer) = synchronized {
    assert(free)

    _free = false
    Thread.sleep(serviceTime.get)
    _free = true
  }

  /** Количество обслуживаемых покупателей в данный момент */
  def customerServiceNowCount = if (free) 0 else 1

}
