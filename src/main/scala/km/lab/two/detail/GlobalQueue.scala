package km.lab.two.detail

import scala.collection.mutable

/**
 * @author phpusr
 *         Date: 08.05.14
 *         Time: 13:47
 */

/**
 * Глобальная очередь деталей
 */
//TODO rename
class GlobalQueue {
  private val queue = mutable.Queue[Detail]()

  def +=(detail: Detail) = queue += detail
  def dequeue = queue.dequeue()
}
