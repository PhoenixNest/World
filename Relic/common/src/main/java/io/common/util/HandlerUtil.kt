package io.common.util

import android.os.Handler
import android.os.Looper

object HandlerUtil {

    private val mainHandler = Handler(Looper.getMainLooper())

    fun postRunnable(runnable: Runnable, delay: Long) {
        if (isMainLooper()) {
            runnable.run()
        } else {
            postToMain(runnable, delay)
        }
    }

    fun removeRunnable(runnable: Runnable?) {
        runnable?.let {
            mainHandler.removeCallbacks(it)
        }
    }

    private fun postToMain(runnable: Runnable, delay: Long) {
        mainHandler.postDelayed(runnable, delay)
    }

    private fun isMainLooper(): Boolean {
        return mainHandler.looper == Looper.getMainLooper()
    }

}