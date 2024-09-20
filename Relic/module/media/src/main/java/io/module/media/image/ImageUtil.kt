package io.module.media.image

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.database.getIntOrNull
import androidx.core.database.getLongOrNull
import androidx.core.database.getStringOrNull
import io.module.media.image.model.ImageInfoModel
import io.module.media.utils.MediaLogUtil

object ImageUtil {

    private const val TAG = "ImageUtil"

    fun queryLocalPhoto(context: Context): List<ImageInfoModel> {
        // Prepare the query parameters.
        val localImageList = mutableListOf<ImageInfoModel>()
        val queryUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val columnId = MediaStore.Images.ImageColumns._ID
        val columnFileName = MediaStore.Images.ImageColumns.DISPLAY_NAME
        val columnFolderName = MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME
        val columnWidth = MediaStore.Images.ImageColumns.WIDTH
        val columnHeight = MediaStore.Images.ImageColumns.HEIGHT
        val columnSize = MediaStore.Images.ImageColumns.SIZE
        val columnMimeType = MediaStore.Images.ImageColumns.MIME_TYPE

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

        // Display images in alphabetical order based on their display name.
        val sortOrder = "${MediaStore.Images.Media.DISPLAY_NAME} ASC"

        context.contentResolver.query(
            /* uri = */queryUri,
            /* projection = */ projection,
            /* selection = */ null,
            /* selectionArgs = */ null,
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
                // Get the values of columns for a given image.
                val idInfo = cursor.getLongOrNull(indexId) ?: -1
                val fileNameInfo = cursor.getStringOrNull(indexFileName)
                val folderNameInfo = cursor.getStringOrNull(indexFolderName)
                val widthInfo = cursor.getIntOrNull(indexWidth)
                val heightInfo = cursor.getIntOrNull(indexHeight)
                val sizeInfo = cursor.getIntOrNull(indexSize)
                val mimeTypeInfo = cursor.getStringOrNull(indexMimeType)
                val contentUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, idInfo)
                val localImageInfoModel = ImageInfoModel(
                    id = idInfo,
                    contentUri = contentUri,
                    fileName = fileNameInfo,
                    folderName = folderNameInfo,
                    width = widthInfo,
                    height = heightInfo,
                    size = sizeInfo,
                    mimeType = mimeTypeInfo
                )

                // Stores column values and the contentUri in a local object
                // that represents the media file.
                localImageList.add(localImageInfoModel)
            }
        }

        return localImageList.toList()
    }

    fun ComponentActivity.createSingleImagePicker(callback: SingleImageCallback): ActivityResultLauncher<PickVisualMediaRequest> {
        return registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri == null) {
                MediaLogUtil.e(TAG, "[Image Picker] Uri is null.")
                callback.onFailed("No media selected")
            } else {
                MediaLogUtil.d(TAG, "[Image Picker] pickup uri: $uri")
                callback.onSucceed(uri)
            }
        }
    }

    fun ComponentActivity.createMultiplyImagePicker(
        count: Int,
        callback: MultiplyImageCallback
    ): ActivityResultLauncher<PickVisualMediaRequest> {
        return registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(count)) { uriList ->
            if (uriList.isEmpty()) {
                MediaLogUtil.e(TAG, "[Image Picker] Uri list is empty.")
                callback.onFailed("No media selected")
            } else {
                MediaLogUtil.d(TAG, "[Image Picker] pickup uriList: $uriList")
                callback.onSucceed(uriList)
            }
        }
    }
}