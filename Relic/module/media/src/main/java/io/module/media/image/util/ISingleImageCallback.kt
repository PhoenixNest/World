package io.module.media.image.util

import android.net.Uri

interface ISingleImageCallback {
    fun onSucceed(uri: Uri?)
    fun onFailed(message: String?)
}