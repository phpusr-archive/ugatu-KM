package experiment.lab2.timer

import java.util.{TimerTask, Timer}

/**
 * @author phpusr
 *         Date: 13.05.14
 *         Time: 11:22
 */

object TimerTest extends App {

  new Timer().schedule(new TimerTask() {
    override def run() {
      println("!!!HELLO")

    }
  }, 0, 100)

}
