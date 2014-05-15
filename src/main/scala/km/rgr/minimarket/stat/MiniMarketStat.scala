package km.rgr.minimarket.stat

import org.dyndns.phpusr.util.stat.Stat

/**
 * @author phpusr
 *         Date: 15.05.14
 *         Time: 17:30
 */

/**
 * Статистика магазина
 * @param queueLength Средняя длина очереди
 */
case class MiniMarketStat(queueLength: Stat)
