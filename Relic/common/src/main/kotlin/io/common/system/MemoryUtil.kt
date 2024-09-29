package io.common.system

import android.app.ActivityManager
import android.content.Context
import android.os.Environment
import android.os.StatFs
import io.common.RelicConstants.Common.UNKNOWN_VALUE_LONG
import io.common.RelicSystemServiceManager.getActivityManger
import io.common.util.LogUtil
import java.io.BufferedReader
import java.io.FileReader

object MemoryUtil {

    private const val TAG = "RelicSystemInfo"

    private val file = Environment.getDataDirectory()
    private val statFs = StatFs(file.path)

    private var totalROMSize = UNKNOWN_VALUE_LONG
    private var totalRAMSize = UNKNOWN_VALUE_LONG

    private const val RAM_FILE_DIR = "/proc/meminfo"

    /* ======================== ROM ======================== */

    fun getTotalROMSize(): Long {
        if (totalROMSize != UNKNOWN_VALUE_LONG) {
            return totalROMSize
        }

        return try {
            val blockSize = statFs.blockSizeLong
            val blockCount = statFs.blockCountLong
            totalROMSize = blockSize * blockCount
            totalROMSize
        } catch (exception: Exception) {
            LogUtil.e(TAG, "[Total ROM] Error, ${exception.message}")
            exception.printStackTrace()
            UNKNOWN_VALUE_LONG
        }
    }

    fun getFreeROMSize(): Long {
        return try {
            val blockSize = statFs.blockSizeLong
            val availableBlocks = statFs.availableBlocksLong
            blockSize * availableBlocks
        } catch (exception: Exception) {
            LogUtil.e(TAG, "[Available ROM] Error, ${exception.message}")
            exception.printStackTrace()
            UNKNOWN_VALUE_LONG
        }
    }

    /* ======================== RAM ======================== */

    fun getTotalRAMSize(): Long {
        if (totalRAMSize != UNKNOWN_VALUE_LONG) {
            return totalRAMSize
        }

        return try {
            val fileReader = FileReader(RAM_FILE_DIR)
            val bufferedReader = BufferedReader(
                /* in = */ fileReader,
                /* sz = */ 2 * 1024
            )
            val memoryLine = bufferedReader.readLine()
            val totalMemory = memoryLine.substring(memoryLine.indexOf("MemTotal:"))
            bufferedReader.close()

            for (i in 0..totalMemory.length) {
                totalMemory.replace("\\D+", "")
            }
            val result = Integer.parseInt(totalMemory) * 1024L
            LogUtil.d(TAG, "[Total RAM] $result")
            totalRAMSize = result
            totalRAMSize
        } catch (exception: Exception) {
            exception.printStackTrace()
            UNKNOWN_VALUE_LONG
        }
    }

    fun getFreeRAMSize(context: Context): Long {
        return try {
            var result = UNKNOWN_VALUE_LONG
            getActivityManger(context)?.let {
                val memoryInfo = ActivityManager.MemoryInfo()
                it.getMemoryInfo(memoryInfo)
                result = memoryInfo.availMem
            }
            result
        } catch (exception: Exception) {
            exception.printStackTrace()
            UNKNOWN_VALUE_LONG
        }
    }
}