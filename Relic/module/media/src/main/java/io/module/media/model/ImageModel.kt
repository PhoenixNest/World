package io.module.media.model

import android.net.Uri

data class ImageModel(
    val id: Long?,
    val contentUri: Uri? = null,
    val fileName: String? = null,
    val folderName: String? = null,
    val width: Int? = null,
    val height: Int? = null,
    val size: Int? = null,
    val mimeType: String? = null
)
