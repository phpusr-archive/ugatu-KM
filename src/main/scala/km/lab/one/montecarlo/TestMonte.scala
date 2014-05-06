package km.lab.one.montecarlo

import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.chart.{NumberAxis, CategoryAxis, LineChart, XYChart}
import scalafx.Includes._
import scalafx.scene.paint.{Color, Paint}
import scalafx.scene.layout.{HBox, VBox, TilePane}
import scalafx.geometry.{Orientation, Insets}
import scalafx.scene.control.Button
import scalafx.event.ActionEvent
import scala.util.Random
import scala.collection.mutable.ListBuffer

/**
 * @author phpusr
 *         Date: 06.05.14
 *         Time: 12:39
 */

/**
 * Вычисление интеграла функции методом Монте-Карло
 */
object TestMonte extends JFXApp {

  /** Количество попадающих точек */
  private var gottenPointsCount = 0
  /** Количество непопадающих точек */
  private var notGottenPointsCount = 0

  /** Кнопка добавления точек на график */
  private val addPointButton = new Button("Add point") {
    defaultButton = true
  }
  /** Кнопка выхода */
  private val exitButton = new Button("Exit") {
    cancelButton = true
  }

  /** График */
  private val lineChart = new LineChart[Number, Number](NumberAxis("X Axis"), NumberAxis("Y Axis")) {
    title = "Line Chart"
    val integralSeries = new XYChart.Series[Number, Number] {
      name = "Data Series 1"
      data = Func.data().map(toChartData)
    }

    data() += integralSeries
  }

  /** Форма */
  stage = new JFXApp.PrimaryStage {
    title = "KM lab1"
    scene = new Scene {
      root = new VBox {
        padding = Insets(10)
        val buttonsBox = new HBox(10) {
          content = List(addPointButton, exitButton)
        }
        content = List(lineChart, buttonsBox)
      }
    }
  }

  addPoints()

  /** Добавление точек на график */
  private def addPoints() = for (i <- 1 to 100) addPoint()

  /** Преобразование координат в точку */
  private def toChartData = (xy: (Double, Double)) => XYChart.Data[Number, Number](xy._1, xy._2)

  /** Добавление точки на график */
  private def addPoint() {
    val s = new XYChart.Series[Number, Number] {
      val x = Random.nextInt(100)
      val y = Random.nextInt(1500) * -1
      data() += toChartData(x, y)
    }
    lineChart.data() += s
  }

  // Обработчики событий

  /** Добавление точки на график */
  addPointButton.onAction = (ae: ActionEvent) => addPoint()
  /** Выход из программы */
  exitButton.onAction = (ae: ActionEvent) => System.exit(0)

}
