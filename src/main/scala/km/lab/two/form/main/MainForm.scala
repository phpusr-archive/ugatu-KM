package km.lab.two.form.main

import scala.swing.{Label, GridBagPanel, MainFrame, SimpleSwingApplication}
import java.awt.{Insets, Dimension}
import km.lab.two.workshop.Workshop

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
  /** Лейбл размера очереди распределения деталей */
  private val detailQueueSizeLabel = defaultLabel()

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

      c.gridy = 2
      layout(new Label("Detail queue:")) = c
      layout(detailQueueSizeLabel) = c

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
  val main = new Workshop
  main.start()

  // Снятие показаний со станков
  new Thread(new Runnable {
    override def run() = {
      while (true) {
        if (main.generateDetailCount.get >= 5) {
          main.stopGenerateDetail()
        }

        warehouuseDetailCountLabel.text = main.warehouse.size.toString
        generateDetailCountLabel.text = main.generateDetailCount.toString
        detailQueueSizeLabel.text = main.detailQueue.size.toString

        main.machineToolDetailQueueSize.zipWithIndex.foreach{case (x, i) =>
          machineToolsQueueSizeList(i).text = s"${x._1} (${x._2})"
        }

        Thread.sleep(1)

      }
    }
  }).start()

}
