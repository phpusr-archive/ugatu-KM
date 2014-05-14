package experiment.props

/**
 * @author phpusr
 *         Date: 12.05.14
 *         Time: 9:51
 */

/**
 * Тестирование возможности изменения внутренних свойств объектов при создании класса
 */
object Main extends App {
  val obj = new Obj {
    prop1 = 2
    func1 = 50
  }
  println(obj)
}

class Obj {

  var prop1 = 0
  private var _prop = 0

  def func1 = _prop
  def func1_=(value: Int) = _prop = value

  override def toString = s"prop1: $prop1, func1: $func1"

}

