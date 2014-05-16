package experiment.rgr

import km.rgr.minimarket.{Customer, CustomerGenerator}
import scala.collection.mutable.ListBuffer

/**
 * @author phpusr
 *         Date: 15.05.14
 *         Time: 15:05
 */

/**
 * Проверка работы генератора покупателей
 */
object CustomerGeneratorTest extends App {

  val list = ListBuffer[Customer]()

  val generator = new CustomerGenerator({
    list += _
  })

  generator.start()

  Thread.sleep(10*1000)

  generator.stop()

  System.exit(0)

}
