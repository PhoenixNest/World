package io.dev.relic.feature.function.gallery.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import io.data.model.pixabay.PixabayDataModel
import io.domain.use_case.pixabay.PixabayUseCase
import io.domain.use_case.pixabay.action.util.WallpaperOrientation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    application: Application,
    private val pixabayUseCase: PixabayUseCase
) : AndroidViewModel(application) {

    companion object {
        private val DEFAULT_ORIENTATION = WallpaperOrientation.VERTICAL
    }

    fun getGalleryPager(
        imageOrientation: WallpaperOrientation = DEFAULT_ORIENTATION
    ): Flow<PagingData<PixabayDataModel>> {
        return pixabayUseCase.searchImagesPaging.invoke(imageOrientation)
            .flow
            .cachedIn(viewModelScope)
            .distinctUntilChanged()
    }
}