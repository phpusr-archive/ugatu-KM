package org.dyndns.phpusr.util.queue

import scala.collection.mutable

/**
 * @author phpusr
 *         Date: 15.05.14
 *         Time: 14:07
 */

/**
 * Потокобезопасная очередь
 */
class MultiThreadQueue[A] {

  private val queue = mutable.Queue[A]()

  def dequeue() = synchronized(queue.dequeue())

  def +=(el: A) = synchronized(queue += el)

  def nonEmpty = synchronized(queue.nonEmpty)

  def size = synchronized(queue.size)

}
