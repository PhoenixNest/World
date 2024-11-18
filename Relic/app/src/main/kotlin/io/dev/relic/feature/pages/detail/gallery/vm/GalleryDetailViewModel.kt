package io.dev.relic.feature.pages.detail.gallery.vm

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.common.ext.ViewModelExt.operationInViewModelScope
import io.core.network.download.DownloadManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class GalleryDetailViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    private val imageDataFlow = MutableStateFlow<Bitmap?>(null)

    companion object {
        private const val TAG = "GalleryDetailViewModel"
    }

    fun downloadImageUrl(imageUrl: String?) {
        if (imageUrl.isNullOrEmpty()) {
            imageDataFlow.tryEmit(null)
            return
        }

        operationInViewModelScope {
            val bitmap = DownloadManager.downloadImage(imageUrl)
            imageDataFlow.tryEmit(bitmap)
        }
    }

    fun getImageDataFlow(): StateFlow<Bitmap?> {
        return imageDataFlow
    }

}