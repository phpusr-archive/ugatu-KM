package km.lab.one.montecarlo

/**
 * @author phpusr
 *         Date: 06.05.14
 *         Time: 13:49
 */

/**
 * Функция для графика
 */
object Func{

  val data = () => {
    for (i <- 1 to 10) yield (i*10.toDouble, Simpson.integral(52, i, Math.E))
  }

}
