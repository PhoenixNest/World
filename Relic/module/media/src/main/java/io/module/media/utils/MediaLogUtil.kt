package io.module.media.utils

import android.util.Log
import io.module.media.BuildConfig

@Suppress("LocalVariableName")
internal object MediaLogUtil {

    private const val DEFAULT_TAG = "Media - LogUtil"

    /**
     * Verbose level logging.
     *
     * @param TAG
     * @param message
     * */
    fun v(TAG: String = DEFAULT_TAG, message: String): Int {
        return if (BuildConfig.DEBUG) Log.v(TAG, message) else -1
    }

    /**
     * Verbose level logging.
     *
     * @param TAG
     * @param message
     * */
    fun d(TAG: String = DEFAULT_TAG, message: String): Int {
        return if (BuildConfig.DEBUG) Log.d(TAG, message) else -1
    }

    /**
     * Info level logging.
     *
     * @param TAG
     * @param message
     * */
    fun i(TAG: String = DEFAULT_TAG, message: String): Int {
        return if (BuildConfig.DEBUG) Log.i(TAG, message) else -1
    }

    /**
     * Warning level logging.
     *
     * @param TAG
     * @param message
     * */
    fun w(TAG: String = DEFAULT_TAG, message: String): Int {
        return if (BuildConfig.DEBUG) Log.w(TAG, message) else -1
    }

    /**
     * Error level logging.
     *
     * @param TAG
     * @param message
     * */
    fun e(TAG: String = DEFAULT_TAG, message: String, throwable: Throwable? = null): Int {
        return if (BuildConfig.DEBUG) Log.e(TAG, message, throwable) else -1
    }

}