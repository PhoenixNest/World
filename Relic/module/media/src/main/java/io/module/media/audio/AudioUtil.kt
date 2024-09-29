package io.module.media.audio

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import androidx.core.database.getIntOrNull
import androidx.core.database.getLongOrNull
import androidx.core.database.getStringOrNull
import io.module.media.audio.model.AudioInfoModel
import java.util.concurrent.TimeUnit

object AudioUtil {

    fun queryLocalAudio(
        context: Context,
        minDuration: Long = 5
    ): List<AudioInfoModel> {
        // Prepare the query parameters.
        val localAudioList = mutableListOf<AudioInfoModel>()
        val queryUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val columnId = MediaStore.Audio.AudioColumns._ID
        val columnFileName = MediaStore.Audio.AudioColumns.DISPLAY_NAME
        val columnFolderName = MediaStore.Audio.AudioColumns.BUCKET_DISPLAY_NAME
        val columnDuration = MediaStore.Audio.AudioColumns.DURATION
        val columnSize = MediaStore.Audio.AudioColumns.SIZE
        val columnMimeType = MediaStore.Audio.AudioColumns.MIME_TYPE

        // Combine query columns.
        val projection = arrayOf(
            columnId,
            columnFileName,
            columnFolderName,
            columnSize,
            columnMimeType
        )

        // Show only audios that are at least 15 seconds in duration.
        val selection = "${MediaStore.Audio.Media.DURATION} >= ?"
        val selectionArgs = arrayOf(
            TimeUnit.MILLISECONDS.convert(minDuration, TimeUnit.SECONDS).toString()
        )

        // Display audios in alphabetical order based on their display name.
        val sortOrder = "${MediaStore.Audio.Media.DISPLAY_NAME} ASC"

        context.contentResolver.query(
            /* uri = */queryUri,
            /* projection = */ projection,
            /* selection = */ selection,
            /* selectionArgs = */ selectionArgs,
            /* sortOrder = */ sortOrder,
            /* cancellationSignal = */ null
        )?.use { cursor ->
            val indexId = cursor.getColumnIndexOrThrow(columnId)
            val indexFileName = cursor.getColumnIndexOrThrow(columnFileName)
            val indexFolderName = cursor.getColumnIndexOrThrow(columnFolderName)
            val indexDuration = cursor.getColumnIndexOrThrow(columnDuration)
            val indexSize = cursor.getColumnIndexOrThrow(columnSize)
            val indexMimeType = cursor.getColumnIndexOrThrow(columnMimeType)

            while (cursor.moveToNext()) {
                // Get the values of columns for a given audio.
                val idInfo = cursor.getLongOrNull(indexId) ?: -1
                val fileNameInfo = cursor.getStringOrNull(indexFileName)
                val folderNameInfo = cursor.getStringOrNull(indexFolderName)
                val durationInfo = cursor.getInt(indexDuration)
                val sizeInfo = cursor.getIntOrNull(indexSize)
                val mimeTypeInfo = cursor.getStringOrNull(indexMimeType)
                val contentUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, idInfo)
                val localAudioInfoModel = AudioInfoModel(
                    id = idInfo,
                    contentUri = contentUri,
                    fileName = fileNameInfo,
                    folderName = folderNameInfo,
                    size = sizeInfo,
                    mimeType = mimeTypeInfo
                )

                // Stores column values and the contentUri in a local object
                // that represents the media file.
                localAudioList.add(localAudioInfoModel)
            }
        }

        return localAudioList.toList()
    }
}