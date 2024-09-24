package io.module.media.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.module.media.image.ImageUtil
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor(
    private val application: Application
) : AndroidViewModel(application) {

    companion object {
        private const val TAG = "AlbumViewModel"
    }

    fun queryLocalPhotos() {
        viewModelScope.launch {
            ImageUtil.queryLocalPhotos(application.applicationContext)
        }
    }
}