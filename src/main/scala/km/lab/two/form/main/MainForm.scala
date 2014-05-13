package km.lab.two.form.main

import scala.swing._
import java.awt.Insets
import km.lab.two.workshop.Workshop
import km.lab.two.constants.Const
import javax.swing.BorderFactory

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

  // Среднее время обработки деталей каждого типа
  private val avgDetailHandlerTimeV1Label = defaultLabel()
  private val avgDetailHandlerTimeV2Label = defaultLabel()

  // Очередь в станках
  private val machineToolsQueueSizeList = for (i <- 1 to 3) yield new Label("0") {
    tooltip = "В очереди (обрабатывается) (средняя загрузка)"
  }

  /** Время работы мастерской */
  private val workedTimeLabel = defaultLabel()

  // Компоненты по умолчанию
  private def defaultLabel() = new Label("0")

  def top = new MainFrame {
    contents = new GridBagPanel {
      val c = new Constraints
      c.insets = new Insets(5, 20, 5, 20)

      layout(new FlowPanel {
        contents += new Label("Отработано времени:")
        contents += workedTimeLabel
      }) = c

      c.gridy = 1
      layout(new GridBagPanel {
        border = BorderFactory.createTitledBorder("Станки")
        val c = new Constraints
        c.insets = new Insets(5, 5, 5, 5)

        for (i <- 0 until machineToolsQueueSizeList.size) {
          c.gridy = i
          layout(new Label(s"A${i+1}:")) = c
          layout(machineToolsQueueSizeList(i)) = c
        }
      }) = c

      layout(new GridBagPanel {
        border = BorderFactory.createTitledBorder("Статистика деталей")
        val c = new Constraints
        c.insets = new Insets(5, 5, 5, 5)

        layout(new Label("Всего поступивших:")) = c
        layout(generateDetailCountLabel) = c

        c.gridy = 1
        layout(new Label("Обработанные (на складе):")) = c
        layout(warehouuseDetailCountLabel) = c

        c.gridy = 2
        layout(new Label("Очередь:")) = c
        layout(detailQueueSizeLabel) = c

      }) = c

      layout(new GridBagPanel {
        border = BorderFactory.createTitledBorder("Среднее время обработки детали")
        val c = new Constraints
        c.insets = new Insets(5, 5, 5, 5)

        layout(new Label("Деталь 1-о типа:")) = c
        layout(avgDetailHandlerTimeV1Label) = c

        c.gridy = 1
        layout(new Label("Деталь 2-о типа:")) = c
        layout(avgDetailHandlerTimeV2Label) = c

      }) = c

    }

    centerOnScreen()
  }

  // Запуск генерации и обработки деталей
  val main = new Workshop(40)
  main.start()

  // Снятие показаний со станков
  new Thread(new Runnable {
    override def run() {
      while (main.enable.get) {

        warehouuseDetailCountLabel.text = main.warehouse.size.toString
        generateDetailCountLabel.text = main.generateDetailCount.toString
        detailQueueSizeLabel.text = main.detailQueue.size.toString

        val avgDetailHandlerTime = main.avgDetailHandlerTime
        avgDetailHandlerTimeV1Label.text = milisToMinutes(avgDetailHandlerTime._1)
        avgDetailHandlerTimeV2Label.text = milisToMinutes(avgDetailHandlerTime._2)

        workedTimeLabel.text = milisToHours(main.workedTime)

        main.machineToolDetailQueueSize.zipWithIndex.foreach{case (x, i) =>
          machineToolsQueueSizeList(i).text = s"${x._1} (${x._2}) (${x._3 formatted "%.2f"})"
        }

        Thread.sleep(100)
      }
    }
  }).start()

  /** мс. -> мин. (с учетом ускорения времени) */
  private def milisToMinutes(milis: Long) = Math.round(milis.toFloat * Const.Acceleration / 1000 / 60).toString + " мин."

  /** мс. -> ч. (с учетом ускорения времени) */
  private def milisToHours(milis: Long) = Math.round(milis.toFloat * Const.Acceleration / 1000 / 60 / 60).toString + " ч."

}
