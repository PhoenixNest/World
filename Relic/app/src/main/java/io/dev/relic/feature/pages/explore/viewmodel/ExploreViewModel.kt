package io.dev.relic.feature.pages.explore.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
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

}