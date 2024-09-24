package io.module.media.video.model

import android.net.Uri
import io.module.media.model.MediaBaseInfoModel

data class VideoInfoModel(
    override val id: Long?,
    override val contentUri: Uri? = null,
    override val fileName: String? = null,
    override val folderName: String? = null,
    val width: Int? = null,
    val height: Int? = null,
    val duration: Int? = null,
    val size: Int? = null,
    val mimeType: String? = null
) : MediaBaseInfoModel(id, contentUri, fileName, folderName)
