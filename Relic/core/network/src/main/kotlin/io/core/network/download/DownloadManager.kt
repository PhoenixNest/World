package io.core.network.download

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import io.common.RelicSystemServiceManager.getSystemDownloadManager
import io.common.system.FileUtil
import io.common.util.LogUtil
import io.core.network.NetworkParameters.MAX_TIMEOUT_CONNECT_DURATION
import io.core.network.NetworkParameters.MAX_TIMEOUT_READ_DURATION
import io.core.network.NetworkParameters.REQUEST_PROPERTY
import io.core.network.NetworkParameters.REQUEST_TYPE_GET
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeout
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

object DownloadManager {

    private const val TAG = "DownloadManager"

    @OptIn(InternalCoroutinesApi::class)
    suspend fun download(
        context: Context,
        url: String,
        savePath: String = FileUtil.getLocalCacheDirPath(context)
    ) {
        withTimeout(MAX_TIMEOUT_CONNECT_DURATION) {
            suspendCancellableCoroutine<Boolean> { continuation ->
                val downloadUrl = URL(url)
                val fileName = FileUtil.getDownloadFileName(url)
                val httpURLConnection = downloadUrl.openConnection() as? HttpURLConnection
                if (httpURLConnection == null) {
                    continuation.tryResume(false)
                    return@suspendCancellableCoroutine
                }

                httpURLConnection.apply {
                    readTimeout = MAX_TIMEOUT_READ_DURATION.toInt()
                    connectTimeout = MAX_TIMEOUT_CONNECT_DURATION.toInt()
                    requestMethod = REQUEST_TYPE_GET
                    setRequestProperty(REQUEST_PROPERTY.first, REQUEST_PROPERTY.second)
                }

                if (httpURLConnection.responseCode == 200) {
                    val inputStream = httpURLConnection.inputStream
                    var fileOutputStream: FileOutputStream? = null
                    try {
                        if (inputStream == null) {
                            continuation.tryResume(false)
                            return@suspendCancellableCoroutine
                        }

                        val file = FileUtil.createCacheFile(context, fileName)
                        fileOutputStream = FileOutputStream(file)

                        val byteArray = byteArrayOf()
                        val length = inputStream.read(byteArray)
                        while (length != -1) {
                            fileOutputStream.write(byteArray, 0, length)
                        }
                    } catch (exception: Exception) {
                        continuation.tryResumeWithException(exception)
                        exception.printStackTrace()
                    } finally {
                        fileOutputStream?.flush()
                        fileOutputStream?.close()
                    }
                }
            }
        }
    }

    fun download(
        context: Context,
        url: String
    ) {
        val downloadUri = Uri.parse(url)
        if (downloadUri == null) {
            LogUtil.e(TAG, "[Download] Filed, invalid download url.")
            return
        }

        val fileName = FileUtil.getDownloadFileName(url)
        val request = DownloadManager.Request(downloadUri).apply {
            setDestinationInExternalPublicDir("", fileName)
        }

        val systemDownloadManager = getSystemDownloadManager(context)
        if (systemDownloadManager == null) {
            LogUtil.e(TAG, "[Download] Filed, can not access system download manager.")
            return
        }

        LogUtil.d(TAG, "[Download] Start...")
        systemDownloadManager.enqueue(request)
    }
}