package km.lab.two.detail

import km.lab.two.machinetool.MachineTool
import km.lab.two.timeslot.Timeslot

/**
 * @author phpusr
 *         Date: 08.05.14
 *         Time: 12:44
 */

/**
 * Операция над деталью
 */
case class Operation(machineTool: MachineTool, handlingTime: Timeslot)

/**
 * Разновидности операций над деталью
 */
object Operation {
  val _1 = Operation(MachineTool.A1, Timeslot(18, 3))
  val _2 = Operation(MachineTool.A2, Timeslot(10, 3))
  val _3 = Operation(MachineTool.A3, Timeslot(12, 5))
  val _4 = Operation(MachineTool.A1, Timeslot(20, 4))
  val _5 = Operation(MachineTool.A2, Timeslot(25, 8))
  val _6 = Operation(MachineTool.A3, Timeslot(12, 4))
}
