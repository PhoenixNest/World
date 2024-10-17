package io.module.media.image.util

import android.net.Uri

interface MultiplyImageCallback {
    fun onSucceed(uriList: List<Uri>)
    fun onFailed(message: String?)
}