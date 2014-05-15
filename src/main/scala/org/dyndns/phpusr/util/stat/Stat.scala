package org.dyndns.phpusr.util.stat

/**
 * @author phpusr
 *         Date: 16.04.14
 *         Time: 19:00
 */

/**
 * Подсчет статистики получения элементов
 */
// TODO add tests
class Stat {
  /** Кол-во элементов */
  private var _elementsCount = 0L
  /** Число выполненных операций для текущего элемента  */
  private var _currentElementCounter = 0L
  /** Число выполненных операций для всех элементов  */
  private var _allElementsCounter = 0L

  // Getters
  def elementsCount = synchronized(_elementsCount)
  def currentElementCounter = synchronized(_currentElementCounter)
  def allElementsCounter = synchronized(_allElementsCounter)

  /**
   * Считать новый элемент
   *
   * Сбросить подсчет текущего
   * Увеличить кол-во элементов
   */
  def newElement() = synchronized {
    _currentElementCounter = 0
    _elementsCount += 1
  }

  /** Увеличить счетчик для текущего элемента */
  def inc() = synchronized {
    _currentElementCounter += 1
    _allElementsCounter += 1
  }

  /** Увеличить значение текущего элемента */
  def add(value: Long) = synchronized {
    _currentElementCounter += value
    _allElementsCounter += value
  }

  /** Считать новый элемент и увеличить значение */
  def newElementAndAdd(value: Long) {
    newElement()
    add(value)
  }

  /** Среднее число операций для элемента */
  def avg = synchronized {
    _allElementsCounter.toFloat / _elementsCount
  }

  /** Сброс статистики */
  def reset() = synchronized {
    _elementsCount = 0
    _currentElementCounter = 0
    _allElementsCounter = 0
  }
}

