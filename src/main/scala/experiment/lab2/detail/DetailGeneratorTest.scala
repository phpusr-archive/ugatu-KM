package experiment.lab2.detail

import km.lab.two.detail.{Detail, DetailType, DetailGenerator}
import km.lab.two.timeslot.Timeslot
import scala.collection.mutable.ListBuffer

/**
 * @author phpusr
 *         Date: 12.05.14
 *         Time: 10:55
 */


object DetailGeneratorTest extends App {

  val details = ListBuffer[Detail]()
  def action(detail: Detail, isGenerate: Boolean) {
    println(">> generate: " + detail)
    details += detail
  }

  val generator = new DetailGenerator(DetailType.V1, action, Timeslot(2, 1))
  generator.start()

  while(details.size < 10) Thread.sleep(100)

  generator.stop()

}
