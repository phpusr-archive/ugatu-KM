package km.rgr.minimarket.form.main

import scala.swing._
import km.rgr.minimarket.MiniMarket
import javax.swing.Timer
import java.awt.event.{ActionEvent, ActionListener}

/**
 * @author phpusr
 *         Date: 15.05.14
 *         Time: 16:16
 */

/**
 * Главаная форма для РГР
 */
object RgrMainForm extends SimpleSwingApplication {

  private val infoTextArea = new TextArea(15, 20)

  private val market = new MiniMarket

  private val stopCustomerGeneratorButton = new Button(Action("Stop customer") {
    market.stopCustomerGenerator()
  })

  def top = new MainFrame {
    contents = new GridBagPanel {
      val c = new Constraints

      layout(infoTextArea) = c

      c.gridy = 1
      layout(stopCustomerGeneratorButton) = c
    }

    centerOnScreen()
  }


  miniMarket()

  def miniMarket() {

    market.start()

    new Timer(100, new ActionListener {
      override def actionPerformed(e: ActionEvent) {
        val info = market.getInfo
        infoTextArea.text = s"Не обслуженные покупатели: ${info.notServiceCustomerCount}" +
          s"\nОбслуженные покупатели: ${info.serviceCustomerCount}" +
          s"\nОчередь у кассы: ${info.queueLength}" +
          s"\nВ кассе: ${info.customerServiceNowCount}"
      }
    }).start()


  }

}
