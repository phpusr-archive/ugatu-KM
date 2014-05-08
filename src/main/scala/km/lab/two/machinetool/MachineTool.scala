package km.lab.two.machinetool

/**
 * @author phpusr
 *         Date: 08.05.14
 *         Time: 12:46
 */

/**
 * Станок
 */
case class MachineTool(name: String)

/**
 * Станки участка цеха
 */
object MachineTool {
  val A1 = MachineTool("A1")
  val A2 = MachineTool("A2")
  val A3 = MachineTool("A3")
}
