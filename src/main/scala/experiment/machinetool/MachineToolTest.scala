package experiment.machinetool

import km.lab.two.machinetool.MachineTool
import km.lab.two.detail.{DetailType, Detail}
import scala.collection.mutable.ListBuffer
import km.lab.two.workshop.Workshop

/**
 * @author phpusr
 *         Date: 12.05.14
 *         Time: 10:04
 */

/**
 * Тестирование работы станка
 */
object MachineToolTest extends App {
  val finDets = ListBuffer[Detail]()

  val main = new Workshop(40)
  main.machineToolDetailQueueSize.zipWithIndex.foreach{case (x, i) =>
    println(s"${x._1}, (${x._2})")
  }


  MachineTool.setAction { (detail, isGenerate) =>

    if (detail.currentOperation.isDefined) {
      println(">>fin: " + detail)
      detail.currentOperation.get.machineTool.addDetail(detail)
    } else {
      println(">>fin all: " + detail)
      finDets += detail
    }
  }

  val details = for (i <- 1 to 0) yield Detail(s"Detail-$i", DetailType.V1)

  MachineTool.startAll()

  details.foreach(MachineTool.A1.addDetail)

  while (finDets.size < details.size) Thread.sleep(100)

  MachineTool.stopAll()
  
}
