package io.module.media.utils

import android.graphics.Bitmap
import android.graphics.Matrix
import androidx.exifinterface.media.ExifInterface

object MediaModifier {

    fun getBitmapDegree(imagePath: String): Int {
        return try {
            val exifInterface = ExifInterface(imagePath)
            val orientation = exifInterface.getAttributeInt(
                /* tag = */ ExifInterface.TAG_ORIENTATION,
                /* defaultValue = */ ExifInterface.ORIENTATION_NORMAL
            )
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> 90
                ExifInterface.ORIENTATION_ROTATE_180 -> 180
                ExifInterface.ORIENTATION_ROTATE_270 -> 270
                else -> 0
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
            0
        }
    }

    fun rotateImageDegree(
        inputBitmap: Bitmap,
        degree: Int
    ): Bitmap? {
        return try {
            val matrix = Matrix()
            matrix.postRotate(degree.toFloat())
            Bitmap.createBitmap(
                /* source = */ inputBitmap,
                /* x = */ 0,
                /* y = */ 0,
                /* width = */ inputBitmap.width,
                /* height = */ inputBitmap.height,
                /* m = */ matrix,
                /* filter = */ true
            )
        } catch (error: OutOfMemoryError) {
            error.printStackTrace()
            inputBitmap
        } catch (exception: Exception) {
            exception.printStackTrace()
            inputBitmap
        } finally {
            inputBitmap.recycle()
        }
    }
}