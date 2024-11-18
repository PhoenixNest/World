package io.core.network.download

import android.app.DownloadManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import io.common.RelicSystemServiceManager.getSystemDownloadManager
import io.common.system.FileUtil
import io.common.util.LogUtil
import io.core.network.NetworkParameters
import io.core.network.NetworkParameters.REQUEST_PROPERTY
import io.core.network.NetworkParameters.REQUEST_TYPE_GET
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeout
import java.io.BufferedInputStream
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL
import kotlin.coroutines.resume

object DownloadManager {

    private const val TAG = "DownloadManager"

    private const val DEFAULT_MAX_BYTE_ARRAY_SIZE = 10 * 1024
    private const val MAX_TIMEOUT_READ_DURATION = NetworkParameters.MAX_TIMEOUT_READ_DURATION * 1000L
    private const val MAX_TIMEOUT_CONNECT_DURATION = NetworkParameters.MAX_TIMEOUT_CONNECT_DURATION * 1000L

    @OptIn(InternalCoroutinesApi::class)
    suspend fun downloadImage(url: String): Bitmap? {
        return withTimeout(MAX_TIMEOUT_CONNECT_DURATION) {
            suspendCancellableCoroutine { continuation ->
                val downloadUrl = URL(url)
                val httpURLConnection = (downloadUrl.openConnection() as? HttpURLConnection)
                if (httpURLConnection == null) {
                    continuation.resume(null)
                    return@suspendCancellableCoroutine
                }

                httpURLConnection.apply {
                    readTimeout = MAX_TIMEOUT_READ_DURATION.toInt()
                    connectTimeout = MAX_TIMEOUT_CONNECT_DURATION.toInt()
                    requestMethod = REQUEST_TYPE_GET
                    setRequestProperty(REQUEST_PROPERTY.first, REQUEST_PROPERTY.second)
                }

                LogUtil.d(TAG, "[Download] URLConnection check confirmed.")
                httpURLConnection.connect()

                try {
                    if (httpURLConnection.responseCode == 200) {
                        LogUtil.d(TAG, "[Download] Response succeed with code 200.")
                        val inputStream = httpURLConnection.inputStream
                        val bufferedInputStream = BufferedInputStream(inputStream)
                        val bitmap = BitmapFactory.decodeStream(bufferedInputStream)
                        continuation.resume(bitmap)
                        LogUtil.d(TAG, "[Download] Response succeed with data: $bitmap")
                    } else {
                        continuation.resume(null)
                    }
                } catch (exception: Exception) {
                    continuation.tryResumeWithException(exception)
                    exception.printStackTrace()
                    continuation.resume(null)
                }
            }
        }
    }

    @OptIn(InternalCoroutinesApi::class)
    suspend fun downloadFile(
        context: Context,
        url: String
    ): String? {
        return withTimeout(MAX_TIMEOUT_CONNECT_DURATION) {
            suspendCancellableCoroutine { continuation: CancellableContinuation<String?> ->
                val downloadUrl = URL(url)
                val fileName = FileUtil.getDownloadFileName(url)
                val httpURLConnection = (downloadUrl.openConnection() as? HttpURLConnection)
                if (httpURLConnection == null) {
                    LogUtil.e(TAG, "[Download] Cast connection to HttpURLConnection failed, fallback.")
                    continuation.resume(null)
                    return@suspendCancellableCoroutine
                }

                httpURLConnection.apply {
                    readTimeout = MAX_TIMEOUT_READ_DURATION.toInt()
                    connectTimeout = MAX_TIMEOUT_CONNECT_DURATION.toInt()
                    requestMethod = REQUEST_TYPE_GET
                    setRequestProperty(REQUEST_PROPERTY.first, REQUEST_PROPERTY.second)
                }

                LogUtil.d(TAG, "[Download] URLConnection check confirmed.")
                httpURLConnection.connect()

                try {
                    if (httpURLConnection.responseCode == 200) {
                        LogUtil.d(TAG, "[Download] Response succeed with code 200.")
                        val inputStream = httpURLConnection.inputStream
                        if (inputStream == null) {
                            LogUtil.e(TAG, "[Download] Open file input stream error, fallback.")
                            continuation.resume(null)
                            return@suspendCancellableCoroutine
                        }

                        val file = FileUtil.createCacheFile(context, fileName)
                        val fileOutputStream: FileOutputStream? = FileOutputStream(file)
                        val contentLength = httpURLConnection.contentLength / 1000
                        val byteArray = ByteArray(contentLength)
                        LogUtil.d(TAG, "[Download] Start to write the input stream into output stream.")

                        while (inputStream.read(byteArray) != -1) {
                            LogUtil.d(TAG, "[Download] Write to output stream.")
                            fileOutputStream?.write(byteArray)
                        }

                        fileOutputStream?.flush()
                        fileOutputStream?.close()
                        continuation.resume(file.absolutePath)
                        LogUtil.d(TAG, "[Download] Output finished, filePath: ${file.absolutePath}")
                    } else {
                        continuation.tryResume(null)
                    }
                } catch (exception: Exception) {
                    continuation.tryResumeWithException(exception)
                    exception.printStackTrace()
                    continuation.resume(null)
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