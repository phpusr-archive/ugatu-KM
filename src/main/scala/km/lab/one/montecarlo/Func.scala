package km.lab.one.montecarlo

/**
 * @author phpusr
 *         Date: 06.05.14
 *         Time: 13:49
 */

/**
 * Функция для графика
 */
object Func {

  /** Нахождение определенного интеграла методом Симпсона */
  val integral = (x: Int) => Simpson.integral(52, x, Math.E)

  /** Набор координат */
  val data = () => {
    for (i <- 1 to 30) yield (i.toDouble, integral(i))
  }


}
