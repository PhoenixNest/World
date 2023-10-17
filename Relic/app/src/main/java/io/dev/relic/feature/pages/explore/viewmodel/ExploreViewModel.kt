package io.dev.relic.feature.pages.explore.viewmodel

import android.app.Application
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.amap.api.services.core.PoiItemV2
import com.amap.api.services.poisearch.PoiResultV2
import com.amap.api.services.poisearch.PoiSearchV2.OnPoiSearchListener
import dagger.hilt.android.lifecycle.HiltViewModel
import io.dev.relic.domain.location.map.amap.AMapPoiCenter
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    /**
     * Indicate the current selected food recipes tab.
     * */
    var currentSelectedBottomSheetTab: Int by mutableIntStateOf(0)

    companion object {
        private const val TAG = "ExploreViewModel"
    }

    fun updateSelectedBottomSheetTab(newIndex: Int) {
        currentSelectedBottomSheetTab = newIndex
    }

    fun fetchPoiData(
        context: Context,
        keyWord: String,
        poiSearchType: String,
        cityCode: String,
        offset: Int,
    ) {
        viewModelScope.launch {
            AMapPoiCenter.fetchPoiData(
                context = context,
                keyWord = keyWord,
                poiSearchType = poiSearchType,
                cityCode = cityCode,
                offset = offset,
                listener = object : OnPoiSearchListener {
                    override fun onPoiSearched(item: PoiResultV2?, rCode: Int) {
                        //
                    }

                    override fun onPoiItemSearched(item: PoiItemV2?, rCode: Int) {
                        //
                    }
                }
            )
        }
    }
}