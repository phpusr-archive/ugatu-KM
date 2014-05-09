package km.lab.two.form.main

import scala.swing.{Label, GridBagPanel, MainFrame, SimpleSwingApplication}
import java.awt.{Insets, Dimension}
import km.lab.two.run.Main

/**
 * @author phpusr
 *         Date: 09.05.14
 *         Time: 13:39
 */

/**
 * Главная форма
 */
object MainForm extends SimpleSwingApplication {

  /** Лейбл кол-ва деталей на складе */
  private val warehouuseDetailCountLabel = defaultLabel()
  /** Лейбл кол-ва всего поступленных деталей */
  private val generateDetailCountLabel = defaultLabel()

  // Очередь в станках
  private val machineToolsQueueSizeList = for (i <- 1 to 3) yield defaultLabel()

  // Компоненты по умолчанию
  private def defaultLabel() = new Label("0")

  def top = new MainFrame {
    contents = new GridBagPanel {
      val c = new Constraints
      c.insets = new Insets(5, 5, 5, 5)

      layout(new Label("Warehouse:")) = c
      layout(warehouuseDetailCountLabel) = c

      c.gridy = 1
      layout(new Label("All generate detail:")) = c
      layout(generateDetailCountLabel) = c

      for (i <- 0 until machineToolsQueueSizeList.size) {
        c.gridx = 2
        c.gridy = i
        layout(new Label(s"A${i+1}")) = c
        c.gridx = 3
        layout(machineToolsQueueSizeList(i)) = c
      }
    }

    size = new Dimension(500, 300)
    centerOnScreen()
  }

  // Запуск генерации и обработки деталей
  val main = new Main
  main.start()

  // Снятие показаний со станков
  new Thread(new Runnable {
    override def run() = {
      while (true) {
        Thread.sleep(100)
        warehouuseDetailCountLabel.text = main.warehouse.size.toString
        generateDetailCountLabel.text = main.generateDetailCount.toString

        println(main.warehouseDetailQueueSize)
        main.warehouseDetailQueueSize.zipWithIndex.foreach{case (x, i) =>
          machineToolsQueueSizeList(i).text = x.toString
        }

        if (main.generateDetailCount >= 30) {
          main.stopGenerateDetail()
        }
      }
    }
  }).start()

}
