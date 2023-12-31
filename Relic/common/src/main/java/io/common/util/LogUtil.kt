package io.common.util

import android.util.Log
import io.common.BuildConfig

object LogUtil {

    private const val DEFAULT_TAG = "Relic - LogUtil"

    /**
     * Verbose level logging.
     *
     * @param TAG
     * @param message
     * */
    fun verbose(TAG: String = DEFAULT_TAG, message: String): Int {
        return if (BuildConfig.DEBUG) Log.v(TAG, message) else -1
    }

    /**
     * Verbose level logging.
     *
     * @param TAG
     * @param message
     * */
    fun debug(TAG: String = DEFAULT_TAG, message: String): Int {
        return if (BuildConfig.DEBUG) Log.d(TAG, message) else -1
    }

    /**
     * Info level logging.
     *
     * @param TAG
     * @param message
     * */
    fun info(TAG: String = DEFAULT_TAG, message: String): Int {
        return if (BuildConfig.DEBUG) Log.i(TAG, message) else -1
    }

    /**
     * Warning level logging.
     *
     * @param TAG
     * @param message
     * */
    fun warning(TAG: String = DEFAULT_TAG, message: String): Int {
        return if (BuildConfig.DEBUG) Log.w(TAG, message) else -1
    }

    /**
     * Error level logging.
     *
     * @param TAG
     * @param message
     * */
    fun error(TAG: String = DEFAULT_TAG, message: String): Int {
        return if (BuildConfig.DEBUG) Log.e(TAG, message) else -1
    }

}