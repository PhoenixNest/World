package io.common.util

import java.time.Clock
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Calendar

object TimeUtil {

    private const val CURRENT_CENTURY_VALUE = 2000

    /**
     * Get the current system time with date-time.
     * */
    fun getCurrentTime(): LocalDateTime {
        return LocalDateTime.now()
    }

    /**
     * Get the current system time with formatted date-time.
     * */
    fun getCurrentFormattedTime(): String {
        val year = LocalDateTime.now().year
        val monthFormat = monthFormat(LocalDateTime.now().month.value)
        val dayOfMonth = LocalDateTime.now().dayOfMonth
        return "$year ${monthFormat}'${dayOfMonth}"
    }

    /**
     * Get the current system time with date-time.
     *
     * @param clock
     * */
    fun getCurrentTime(clock: Clock): LocalDateTime {
        return LocalDateTime.now(clock)
    }

    /**
     * Get the current system time with date-time.
     *
     * @param zone
     * */
    fun getCurrentTime(zone: ZoneId?): LocalDateTime {
        return LocalDateTime.now(zone ?: ZoneId.systemDefault())
    }

    /**
     * Get the current system time with milliseconds.
     * */
    fun getCurrentTimeInMillis(): Long {
        return Calendar.getInstance().timeInMillis
    }

    /**
     * Get today's zero-hour with timestamp.
     * */
    fun getTodayZero(): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = getCurrentTimeInMillis()
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }

    /**
     * Get tomorrow's zero-hour timestamp.
     * */
    fun getNextDayZero(): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = getCurrentTimeInMillis()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        calendar.set(
            /* year = */ year,
            /* month = */ month,
            /* date = */ day + 1,
            /* hourOfDay = */ 0,
            /* minute = */ 0,
            /* second = */ 0
        )
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }

    /**
     * Formatted the month value into short-text value.
     *
     * @param monthValue
     * */
    private fun monthFormat(monthValue: Int): String {
        return when (monthValue) {
            1 -> "Jan"
            2 -> "Feb"
            3 -> "Mar"
            4 -> "Apr"
            5 -> "May"
            6 -> "Jun"
            7 -> "Jul"
            8 -> "Aug"
            9 -> "Sept"
            10 -> "Oct"
            11 -> "Nov"
            12 -> "Dec"
            else -> "Jan"
        }
    }
}