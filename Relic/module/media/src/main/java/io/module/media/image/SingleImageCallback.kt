package io.module.media.image

import android.net.Uri

interface SingleImageCallback {
    fun onSucceed(uri: Uri?)
    fun onFailed(message: String?)
}