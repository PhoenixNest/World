package io.module.media.utils

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.exifinterface.media.ExifInterface
import java.io.FileNotFoundException

/**
 * Media util to retrieve the media info.
 *
 * Such as: User image's latitude longitude data.
 * */
object MediaInfoTracker {

    private const val TAG = "MediaInfoTracker"

    @RequiresApi(29)
    fun retrieveImageExifInfo(
        context: Context,
        uri: Uri
    ): ExifInterface? {
        var exifInfo: ExifInterface? = null
        try {
            val originalUri = MediaStore.setRequireOriginal(uri)
            context.contentResolver.openInputStream(originalUri)?.use { inputStream ->
                exifInfo = ExifInterface(inputStream)
            }
        } catch (exception: FileNotFoundException) {
            MediaLogUtil.e(TAG, "[Retrieve EXIF info] Error, file not found, message: ${exception.message}")
            exception.printStackTrace()
        } catch (exception: Exception) {
            MediaLogUtil.e(TAG, "[Retrieve EXIF info] Error, message: ${exception.message}")
            exception.printStackTrace()
        }

        return exifInfo
    }

    @RequiresApi(29)
    fun retrieveImageLatLongInfo(
        context: Context,
        uri: Uri
    ): DoubleArray? {
        var imageLatLong: DoubleArray? = null
        try {
            retrieveImageExifInfo(context, uri)?.run {
                imageLatLong = latLong
            }
        } catch (exception: FileNotFoundException) {
            MediaLogUtil.e(TAG, "[Retrieve Image LatLong info] Error, file not found, message: ${exception.message}")
            exception.printStackTrace()
        } catch (exception: IllegalAccessException) {
            MediaLogUtil.e(TAG, "[Retrieve Image LatLong info] Error, no permission, message: ${exception.message}")
            exception.printStackTrace()
        } catch (exception: Exception) {
            MediaLogUtil.e(TAG, "[Retrieve Image LatLong info] Error, message: ${exception.message}")
            exception.printStackTrace()
        }

        return imageLatLong
    }
}