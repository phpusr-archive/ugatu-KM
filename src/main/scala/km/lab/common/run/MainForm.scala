package km.lab.common.run

import scala.swing._
import km.lab.one.montecarlo.MonteCarlo
import km.lab.two.form.main.Lab2MainForm

/**
 * @author phpusr
 *         Date: 14.05.14
 *         Time: 13:10
 */

/**
 * Главная форма
 *
 * Выбор запускаемой лабы
 */
object MainForm extends SimpleSwingApplication {

  private val args = Array[String]()

  def top = new MainFrame {
    contents = new GridBagPanel {
      val c = new Constraints
      c.insets = new Insets(5, 5, 5, 5)

      // Лаба 1
      layout(new Button(Action("Lab 1") {
        MonteCarlo.main(args)
      })) = c

      // Лаба 2
      c.gridy = 1
      layout(new Button(Action("Lab 2") {
        Lab2MainForm.main(args)
      })) = c
    }

    centerOnScreen()
    size = new Dimension(400, 300)
  }

}
