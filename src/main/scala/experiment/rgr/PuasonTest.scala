package experiment.rgr

/**
 * @author phpusr
 *         Date: 16.05.14
 *         Time: 14:51
 */

/**
 * Тестирование закона Пуассона
 * http://bib.convdocs.org/v10992/%D1%81%D0%BC%D0%BE%D0%BB%D0%B8%D1%87_%D1%81.%D0%B2.,_%D1%81%D0%BC%D0%BE%D0%BB%D0%B8%D1%87_%D0%BA.%D1%81._%D1%80%D0%B5%D1%88%D0%B5%D0%BD%D0%B8%D0%B5_%D0%B3%D0%BE%D1%80%D0%BD%D0%BE-%D0%B3%D0%B5%D0%BE%D0%BB%D0%BE%D0%B3%D0%B8%D1%87%D0%B5%D1%81%D0%BA%D0%B8%D1%85_%D0%B7%D0%B0%D0%B4%D0%B0%D1%87_%D0%BC%D0%B5%D1%82%D0%BE%D0%B4%D0%BE%D0%BC_%D0%BC%D0%BE%D0%BD%D1%82%D0%B5-%D0%BA%D0%B0%D1%80%D0%BB%D0%BE?page=3
 */
object PuasonTest extends App {

  def factorial(n: Int): Int = {
    if (n <= 1) 1
    else n * factorial(n - 1)
  }

  factorial(5)

  def puason(a: Int, k: Int) = {
    val part1 = Math.pow(Math.E, -1 * a)
    //println(s"part1: $part1")

    var sum = 0.0
    for (i <- 0 to k) {
      sum += Math.pow(a, i) / factorial(i).toDouble
      //println(s"sum $i: $sum")
    }
    part1 * sum
  }

  val a = 20
  (1 to 5).foreach { i =>
    println(s"$i: ${puason(a, i)}")
  }

}
