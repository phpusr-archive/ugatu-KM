package experiment.multithread

import scala.collection.mutable
import java.util.concurrent.atomic.AtomicInteger

/**
 * @author phpusr
 *         Date: 09.05.14
 *         Time: 16:55
 */

/**
 * Эксперимент с очередью
 * Воспроизведение моего кода
 */
object Multi2Test extends App {

  val queue = mutable.Queue(1, 2, 3, 4, 5)

  var work = true

  var index = new AtomicInteger(5)

  // Поток добавления в очередь
  val th0 = Thrd.createThread {
    while(work) {
      queue.synchronized {
        queue += index.addAndGet(1)
      }
      Thread.sleep(200)
    }
  }

  // Поток добавления в очередь
  val th01 = Thrd.createThread {
    while(work) {
      queue.synchronized {
        queue += index.addAndGet(1)
      }
      Thread.sleep(200)
    }
  }

  // Поток извлечения из очереди
  val th1 = Thrd.createThread {
    while(work) {
      queue.synchronized {
        if (queue.nonEmpty) {
          println("th1: " + queue.dequeue())
        }
        if (queue.isEmpty) work = false
      }
      Thread.sleep(500)
    }
  }

  // Поток извлечения из очереди
  val th2 = Thrd.createThread {
    while(work) {
      queue.synchronized {
        if (queue.nonEmpty) {
          println("th2: " + queue.dequeue())
        }
        if (queue.isEmpty) work = false
      }
      Thread.sleep(500)
    }
  }

  th0.join()
  th01.join()
  th1.join()
  th2.join()
  println(queue.toList)

}
