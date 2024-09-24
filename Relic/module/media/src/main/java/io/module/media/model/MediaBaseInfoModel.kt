package io.module.media.model

import android.net.Uri

open class MediaBaseInfoModel(
    open val id: Long?,
    open val contentUri: Uri? = null,
    open val fileName: String? = null,
    open val folderName: String? = null
)