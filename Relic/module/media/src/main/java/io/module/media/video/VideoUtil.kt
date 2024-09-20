package io.module.media.video

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import androidx.core.database.getIntOrNull
import androidx.core.database.getLongOrNull
import androidx.core.database.getStringOrNull
import io.module.media.video.model.VideoInfoModel
import java.util.concurrent.TimeUnit

object VideoUtil {

    fun queryLocalVideo(
        context: Context,
        minVideoDuration: Long = 5
    ): List<VideoInfoModel> {
        // Prepare the query parameters.
        val localVideoList = mutableListOf<VideoInfoModel>()
        val queryUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        val columnId = MediaStore.Video.VideoColumns._ID
        val columnFileName = MediaStore.Video.VideoColumns.DISPLAY_NAME
        val columnFolderName = MediaStore.Video.VideoColumns.BUCKET_DISPLAY_NAME
        val columnWidth = MediaStore.Video.VideoColumns.WIDTH
        val columnHeight = MediaStore.Video.VideoColumns.HEIGHT
        val columnSize = MediaStore.Video.VideoColumns.SIZE
        val columnMimeType = MediaStore.Video.VideoColumns.MIME_TYPE

        // Combine query columns.
        val projection = arrayOf(
            columnId,
            columnFileName,
            columnFolderName,
            columnWidth,
            columnHeight,
            columnSize,
            columnMimeType
        )

        // Show only videos that are at least 5 minutes in duration.
        val selection = "${MediaStore.Video.Media.DURATION} >= ?"
        val selectionArgs = arrayOf(
            TimeUnit.MILLISECONDS.convert(minVideoDuration, TimeUnit.MINUTES).toString()
        )

        // Display videos in alphabetical order based on their display name.
        val sortOrder = "${MediaStore.Video.Media.DISPLAY_NAME} ASC"

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
            val indexWidth = cursor.getColumnIndexOrThrow(columnWidth)
            val indexHeight = cursor.getColumnIndexOrThrow(columnHeight)
            val indexSize = cursor.getColumnIndexOrThrow(columnSize)
            val indexMimeType = cursor.getColumnIndexOrThrow(columnMimeType)

            while (cursor.moveToNext()) {
                // Get values of columns for a given image.
                val idInfo = cursor.getLongOrNull(indexId) ?: -1
                val fileNameInfo = cursor.getStringOrNull(indexFileName)
                val folderNameInfo = cursor.getStringOrNull(indexFolderName)
                val widthInfo = cursor.getIntOrNull(indexWidth)
                val heightInfo = cursor.getIntOrNull(indexHeight)
                val sizeInfo = cursor.getIntOrNull(indexSize)
                val sizeMimeType = cursor.getStringOrNull(indexMimeType)
                val contentUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, idInfo)
                val localVideoInfoModel = VideoInfoModel(
                    id = idInfo,
                    contentUri = contentUri,
                    fileName = fileNameInfo,
                    folderName = folderNameInfo,
                    width = widthInfo,
                    height = heightInfo,
                    size = sizeInfo,
                    mimeType = sizeMimeType
                )

                // Stores column values and the contentUri in a local object
                // that represents the media file.
                localVideoList.add(localVideoInfoModel)
            }
        }

        return localVideoList.toList()
    }
}