package io.dev.android.game.util

object ValueUtil {

    fun getIntListFromString(strings: Array<String>): List<Int> {
        val list = arrayListOf<Int>()
        for (item in strings) {
            try {
                list.add(item.toInt())
            } catch (exception: Exception) {
                list.clear()
                break
            }
        }

        return list
    }
}