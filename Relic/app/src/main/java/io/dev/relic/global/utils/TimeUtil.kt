package io.dev.relic.global.utils

import java.time.Clock
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Calendar

object TimeUtil {

    fun getCurrentTime(): LocalDateTime {
        return LocalDateTime.now()
    }

    /**
     * Get the current system time with milliseconds.
     * */
    fun getCurrentTime(clock: Clock): LocalDateTime {
        return LocalDateTime.now(clock)
    }

    /**
     * Get the current system time with milliseconds.
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
        val calendar: Calendar = Calendar.getInstance()
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
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = getCurrentTimeInMillis()
        val year: Int = calendar.get(Calendar.YEAR)
        val month: Int = calendar.get(Calendar.MONTH)
        val day: Int = calendar.get(Calendar.DAY_OF_MONTH)
        calendar.set(year, month, day + 1, 0, 0, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }
}