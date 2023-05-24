package io.dev.relic.global.utils

import java.util.Calendar

object TimeUtil {

    /**
     * Get the current system time.
     * */
    fun getCurrentTime(): Long {
        return Calendar.getInstance().timeInMillis
    }

    /**
     * Get today's zero-hour timestamp.
     * */
    fun getTodayZero(): Long {
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = getCurrentTime()
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
        calendar.timeInMillis = getCurrentTime()
        val year: Int = calendar.get(Calendar.YEAR)
        val month: Int = calendar.get(Calendar.MONTH)
        val day: Int = calendar.get(Calendar.DAY_OF_MONTH)
        calendar.set(year, month, day + 1, 0, 0, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }
}