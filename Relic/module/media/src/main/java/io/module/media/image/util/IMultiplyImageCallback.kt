package io.module.media.image.util

import android.net.Uri

interface IMultiplyImageCallback {
    fun onSucceed(uriList: List<Uri>)
    fun onFailed(message: String?)
}