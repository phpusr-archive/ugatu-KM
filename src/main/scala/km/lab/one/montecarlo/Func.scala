package km.lab.one.montecarlo

import km.lab.one.montecarlo.integral.Integral

/**
 * @author phpusr
 *         Date: 06.05.14
 *         Time: 13:49
 */

/**
 * Функция для графика
 */
object Func {

  val valA = 5.0
  val valB = 15.0
  val valL = 15.0
  val valN = 100.0
  val valH = (valB - valA) / valN

  /** Нахождение определенного интеграла методом Симпсона */
  val integral = (x: Double) => Integral.calc(valL, valN, x)

  /** Набор координат */
  val data = () => {
    (0 until 200).map { i =>
      val x = Integral.calcItemX(valA, valH, i)
      val y = integral(x)
      (x, y)
    }
  }


}
