package km.rgr.minimarket.stat

import org.dyndns.phpusr.util.stat.Stat

/**
 * @author phpusr
 *         Date: 15.05.14
 *         Time: 17:30
 */

/**
 * Статистика магазина
 * @param cashierUpTime Вероятность работы кассира
 * @param queueLength Средняя длина очереди
 * @param customerCount Среднее число покупателей
 * @param waitServiceTime Среднее время ожидания обслуживания
 */
case class MiniMarketStat(cashierUpTime: Stat, queueLength: Stat, customerCount: Stat, waitServiceTime: Stat)
