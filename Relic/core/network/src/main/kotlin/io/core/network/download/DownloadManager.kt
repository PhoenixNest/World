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
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeout
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

object DownloadManager {

    private const val TAG = "DownloadManager"

    private const val DEFAULT_MAX_BYTE_ARRAY_SIZE = 10 * 1024

    @OptIn(InternalCoroutinesApi::class)
    suspend fun download(
        context: Context,
        url: String
    ): String? {
        return withTimeout(MAX_TIMEOUT_CONNECT_DURATION) {
            suspendCancellableCoroutine { continuation: CancellableContinuation<String?> ->
                val downloadUrl = URL(url)
                val fileName = FileUtil.getDownloadFileName(url)
                val httpURLConnection = downloadUrl.openConnection() as? HttpURLConnection
                if (httpURLConnection == null) {
                    LogUtil.e(TAG, "[Download] Cast connection to HttpURLConnection failed, fallback.")
                    continuation.tryResume(null)
                    return@suspendCancellableCoroutine
                }

                httpURLConnection.apply {
                    readTimeout = MAX_TIMEOUT_READ_DURATION.toInt() * 1000
                    connectTimeout = MAX_TIMEOUT_CONNECT_DURATION.toInt() * 1000
                    requestMethod = REQUEST_TYPE_GET
                    setRequestProperty(REQUEST_PROPERTY.first, REQUEST_PROPERTY.second)
                }
                LogUtil.d(TAG, "[Download] URLConnection check confirmed.")

                if (httpURLConnection.responseCode == 200) {
                    LogUtil.d(TAG, "[Download] Response succeed with code 200.")
                    val inputStream = httpURLConnection.inputStream
                    var fileOutputStream: FileOutputStream? = null
                    try {
                        if (inputStream == null) {
                            LogUtil.e(TAG, "[Download] Open file input stream error, fallback.")
                            continuation.tryResume(null)
                            return@suspendCancellableCoroutine
                        }

                        val file = FileUtil.createCacheFile(context, fileName)
                        fileOutputStream = FileOutputStream(file)

                        val contentLength = httpURLConnection.contentLength  / 1000

                        val byteArray = ByteArray(contentLength)
                        LogUtil.d(TAG, "[Download] Start to write the input stream into output stream.")

                        while (inputStream.read(byteArray) != -1) {
                            LogUtil.d(TAG, "[Download] Write to output stream.")
                            fileOutputStream.write(byteArray)
                        }

                        fileOutputStream.flush()
                        continuation.tryResume(file.absolutePath)
                        LogUtil.d(TAG, "[Download] Output finished, filePath: ${file.absolutePath}")
                    } catch (exception: Exception) {
                        continuation.tryResumeWithException(exception)
                        exception.printStackTrace()
                        continuation.tryResume(null)
                    } finally {
                        fileOutputStream?.flush()
                        fileOutputStream?.close()
                    }
                } else {
                    continuation.tryResume(null)
                }
            }
        }
    }

    fun downloadBySystem(
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