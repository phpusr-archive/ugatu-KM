package experiment.lab2.multithread


/**
 * @author phpusr
 *         Date: 09.05.14
 *         Time: 16:10
 */

object Thrd {
  def createThread(action: => Unit) = {
    val th = new Thread(new Runnable {
      override def run() = action
    })
    th.start()
    th
  }
}

case class Value(var value: Int) {
  def inc() = synchronized {value += 1}
  def dec() = synchronized {value -= 1}
}

object MultiThreadTest extends App {

  var value = Value(0)

  val th = Thrd.createThread {
    for (i <- 1 to 50) value.inc()
  }

  val th2 = Thrd.createThread {
    for (i <- 1 to 20) value.dec()
  }

  for (i <- 1 to 50) value.inc()

  th.join()
  th2.join()
  println(value)



}
