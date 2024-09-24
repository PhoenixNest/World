package io.module.media.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.FileNotFoundException
import java.io.IOException

object MediaLoader {

    fun loadImageFromUri(
        context: Context,
        uri: Uri
    ) {
        try {
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                val bitmapOptions = BitmapFactory.Options()
                bitmapOptions.inJustDecodeBounds = true
                bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888
                BitmapFactory.decodeStream(
                    /* is = */ inputStream,
                    /* outPadding = */ null,
                    /* opts = */ bitmapOptions
                )
            }
        } catch (exception: FileNotFoundException) {
            exception.printStackTrace()
        } catch (exception: IOException) {
            exception.printStackTrace()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }
}