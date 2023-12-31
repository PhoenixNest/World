package io.common.system

import android.app.ActivityManager
import android.content.Context
import android.os.Environment
import android.os.StatFs
import io.common.RelicConstants.Common.UNKNOWN_VALUE_LONG
import io.common.RelicSystemServiceManager.getActivityManger
import io.common.util.LogUtil
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

object MemoryUtil {

    private const val TAG = "RelicSystemInfo"

    private val file: File = Environment.getDataDirectory()
    private val statFs = StatFs(file.path)

    private var totalROMSize: Long = UNKNOWN_VALUE_LONG
    private var totalRAMSize: Long = UNKNOWN_VALUE_LONG

    private const val RAM_FILE_DIR = "/proc/meminfo"

    /* ======================== ROM ======================== */

    fun getTotalROMSize(): Long {
        if (totalROMSize != UNKNOWN_VALUE_LONG) {
            return totalROMSize
        }

        return try {
            val blockSize: Long = statFs.blockSizeLong
            val blockCount: Long = statFs.blockCountLong
            totalROMSize = blockSize * blockCount
            totalROMSize
        } catch (exception: Exception) {
            LogUtil.error(TAG, "[Total ROM] Error, ${exception.message}")
            exception.printStackTrace()
            UNKNOWN_VALUE_LONG
        }
    }

    fun getFreeROMSize(): Long {
        return try {
            val blockSize: Long = statFs.blockSizeLong
            val availableBlocks: Long = statFs.availableBlocksLong
            blockSize * availableBlocks
        } catch (exception: Exception) {
            LogUtil.error(TAG, "[Available ROM] Error, ${exception.message}")
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
            val memoryLine: String = bufferedReader.readLine()
            val totalMemory: String = memoryLine.substring(memoryLine.indexOf("MemTotal:"))
            bufferedReader.close()

            for (i: Int in 0..totalMemory.length) {
                totalMemory.replace("\\D+", "")
            }
            val result: Long = Integer.parseInt(totalMemory) * 1024L
            LogUtil.debug(TAG, "[Total RAM] $result")
            totalRAMSize = result
            totalRAMSize
        } catch (exception: Exception) {
            exception.printStackTrace()
            UNKNOWN_VALUE_LONG
        }
    }

    fun getFreeRAMSize(context: Context): Long {
        return try {
            var result: Long = UNKNOWN_VALUE_LONG
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