package km.lab.one.montecarlo

import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.chart.{NumberAxis, LineChart, XYChart}
import scalafx.Includes._
import scalafx.scene.layout.{HBox, VBox}
import scalafx.geometry.Insets
import scalafx.scene.control.{TextField, Label, Button}
import scalafx.event.ActionEvent
import scala.util.Random

/**
 * @author phpusr
 *         Date: 06.05.14
 *         Time: 12:39
 */

/**
 * Вычисление интеграла функции методом Монте-Карло
 */
object MonteCarlo extends JFXApp {

  /** Количество попадающих точек */
  private var gottenPointsCount = 0
  /** Количество непопадающих точек */
  private var notGottenPointsCount = 0

  /** Поле ввода кол-ва добавляемых точек */
  private val addPointsCountTextField = new TextField {
    text.value = "20"
    prefWidth = 40
  }
  /** Кнопка добавления точек на график */
  private val addPointButton = new Button("Add points") {
    defaultButton = true
  }
  /** Кнопка выхода */
  private val exitButton = new Button("Exit") {
    cancelButton = true
  }

  /** Лейбл с информацией о площади */
  private val areaLabel = new Label
  /** Лейбл с информацией о количестве точек */
  private val pointsCountLabel = new Label
  /** Лейбл с иформацией о % занимаемой части */
  private val partLabel = new Label

  // График
  private val (xLowerBounds, xUpperBounds, xTickUnit)  = (5, 25, 1)
  private val (yLowerBounds, yUpperBounds, yTickUnit)  = (0, 5000, 500)
  private val lineChart = new LineChart[Number, Number](NumberAxis("X Axis", xLowerBounds, xUpperBounds, xTickUnit), NumberAxis("Y Axis", yLowerBounds, yUpperBounds, yTickUnit)) {
  //private val lineChart = new LineChart[Number, Number](NumberAxis("X Axis"), NumberAxis("Y Axis")) {
    title = "Line Chart"
    val integralSeries = new XYChart.Series[Number, Number] {
      name = "Integral"
      data = Func.data().map(toChartData)
    }

    data() += integralSeries
  }

  /** Форма */
  stage = new JFXApp.PrimaryStage {
    title = "KM lab1"
    scene = new Scene {
      root = new VBox(10) {
        padding = Insets(10)
        val labelsBox = new HBox(30) {
          content = List(pointsCountLabel, areaLabel, partLabel)
        }
        val buttonsBox = new HBox(10) {
          content = List(addPointsCountTextField, addPointButton, exitButton)
        }
        content = List(lineChart, labelsBox, buttonsBox)
      }
    }
  }

  //-------------------------- Init --------------------------
  addPoints(100)
  //-------------------------- End init --------------------------

  /** Добавление точек на график */
  private def addPoints(count: Int) = for (i <- 1 to count) addPoint()

  /** Преобразование координат в точку */
  private def toChartData = (xy: (Double, Double)) => XYChart.Data[Number, Number](xy._1, xy._2)

  /** Добавление точки на график */
  private def addPoint() {
    val s = new XYChart.Series[Number, Number] {
      val x = Random.nextInt(xUpperBounds)
      val y = Random.nextInt(yUpperBounds)
      data() += toChartData(x, y)

      val yCalc = Func.integral(x)
      if (false) println(s"x: $x, y: $y, yCalc: $yCalc")

      if (y <= yCalc) gottenPointsCount += 1 else notGottenPointsCount += 1
    }
    lineChart.data() += s

    // Обновление информации на лейблах
    pointsCountLabel.text = s"Gotten: $gottenPointsCount   Not gotten: $notGottenPointsCount"
    areaLabel.text = s"Box area: $boxArea   Req. area: $requiredArea"
    partLabel.text = s"%: ${(requiredArea.toDouble / boxArea * 100) formatted "%1.2f"}"
  }

  /** Площадь области, в которой находится график */
  private def boxArea = (xUpperBounds - xLowerBounds).abs * (yUpperBounds - yLowerBounds).abs

  /** Искомая площадь фигуры */
  private def requiredArea = boxArea * gottenPointsCount / (gottenPointsCount + notGottenPointsCount)

  /** Кол-во введенных точек для добавления */
  private def getAddPointsCount = addPointsCountTextField.text.value.toInt

  // Обработчики событий

  /** Добавление точки на график */
  addPointButton.onAction = (ae: ActionEvent) => addPoints(getAddPointsCount)
  /** Выход из программы */
  exitButton.onAction = (ae: ActionEvent) => System.exit(0)

}
