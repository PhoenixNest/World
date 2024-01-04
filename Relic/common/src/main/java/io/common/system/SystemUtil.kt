package io.common.system

import android.content.Context
import android.os.Build
import io.common.RelicConstants.Common.ANDROID
import io.common.RelicConstants.Common.UNKNOWN_VALUE_INT
import io.common.RelicConstants.Common.UNKNOWN_VALUE_LONG
import io.common.RelicConstants.Common.UNKNOWN_VALUE_STRING
import io.common.util.LogUtil
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

object SystemUtil {

    private const val TAG = "SystemInfoUtil"

    private var brandInfo: String = UNKNOWN_VALUE_STRING
    private var modelInfo: String = UNKNOWN_VALUE_STRING
    private var boardInfo: String = UNKNOWN_VALUE_STRING

    private var totalAvailableProcessors: Int = UNKNOWN_VALUE_INT
    private var totalJVMMemory: Long = UNKNOWN_VALUE_LONG

    fun runShell(command: String): String {
        return try {
            val process: Process = Runtime.getRuntime().exec(command)
            val inputStream: InputStream = process.inputStream
            val bufferedReader = BufferedReader(
                /* in = */ InputStreamReader(inputStream),
                /* sz = */ 2 * 1024
            )
            val processResult: String = bufferedReader.readLine()
            bufferedReader.close()
            inputStream.close()

            processResult
        } catch (exception: Exception) {
            LogUtil.e(TAG, "[Shell] ${exception.message}")
            exception.printStackTrace()
            UNKNOWN_VALUE_STRING
        }
    }

    fun getBrandInfo(context: Context): String {
        if (brandInfo != UNKNOWN_VALUE_STRING) {
            return brandInfo
        }

        return try {
            brandInfo = Build.BRAND ?: UNKNOWN_VALUE_STRING
            brandInfo
        } catch (exception: Exception) {
            LogUtil.e(TAG, "[Brand] Error, ${exception.message}")
            exception.printStackTrace()
            UNKNOWN_VALUE_STRING
        }
    }

    fun getPhoneBoardInfo(): String {
        if (boardInfo != UNKNOWN_VALUE_STRING) {
            return boardInfo
        }

        return try {
            boardInfo = Build.BOARD ?: UNKNOWN_VALUE_STRING
            boardInfo
        } catch (exception: Exception) {
            LogUtil.e(TAG, "[Board] Error, ${exception.message}")
            exception.printStackTrace()
            UNKNOWN_VALUE_STRING
        }
    }

    fun getPhoneModelInfo(): String {
        if (modelInfo != UNKNOWN_VALUE_STRING) {
            return modelInfo
        }

        return try {
            modelInfo = Build.MODEL ?: UNKNOWN_VALUE_STRING
            modelInfo
        } catch (exception: Exception) {
            LogUtil.e(TAG, "[Phone Model] Error, ${exception.message}")
            exception.printStackTrace()
            UNKNOWN_VALUE_STRING
        }
    }

    fun getOSInfo(): String {
        return try {
            "$ANDROID ${Build.VERSION.RELEASE}"
        } catch (exception: Exception) {
            LogUtil.e(TAG, "[OS] Error, ${exception.message}")
            exception.printStackTrace()
            UNKNOWN_VALUE_STRING
        }
    }

    fun getAvailableProcessors(): Int {
        if (totalAvailableProcessors != UNKNOWN_VALUE_INT) {
            return totalAvailableProcessors
        }

        return try {
            totalAvailableProcessors = Runtime.getRuntime().availableProcessors()
            totalAvailableProcessors
        } catch (exception: Exception) {
            exception.printStackTrace()
            UNKNOWN_VALUE_INT
        }
    }

    fun getTotalJVMMemory(): Long {
        if (totalJVMMemory != UNKNOWN_VALUE_LONG) {
            return totalJVMMemory
        }

        return try {
            totalJVMMemory = Runtime.getRuntime().totalMemory()
            totalJVMMemory
        } catch (exception: Exception) {
            exception.printStackTrace()
            UNKNOWN_VALUE_LONG
        }
    }

    fun getJVMFreeMemory(): Long {
        return try {
            Runtime.getRuntime().freeMemory()
        } catch (exception: Exception) {
            exception.printStackTrace()
            UNKNOWN_VALUE_LONG
        }

    }

}