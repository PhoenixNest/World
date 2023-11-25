package io.dev.relic.feature.activities.splash.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.module.ad.admob.splash.AdmobSplashAdHelper
import io.dev.relic.global.RelicLifecycleObserver
import io.common.util.LogUtil
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    /**
     * Indicate the loading status of the Splash-Ad
     * */
    private var isAdLoading: Boolean = false

    companion object {
        private const val TAG = "SplashViewModel"
    }

    fun execute(
        context: Context,
        onSplashProcessEnd: () -> Unit
    ) {
        loadSplashAd(
            context = context,
            onAdClosed = onSplashProcessEnd
        )
    }

    private fun loadSplashAd(
        context: Context,
        onAdClosed: () -> Unit
    ) {
        // There is no need to display ads if the app is launched for the first time.
        if (RelicLifecycleObserver.isFirstColdStart) {
            LogUtil.warning(TAG, "[Load Splash-Ad] No need to request the Ad if the app is launched for the first time")
            onAdClosed.invoke()
            return
        }

        // If the Ad is currently loading, there is no need to request the Ad twice.
        if (isAdLoading) {
            LogUtil.warning(TAG, "[Load Splash-Ad] No need to request the Ad twice")
            return
        }

        viewModelScope.launch {
            isAdLoading = true
            io.module.ad.admob.splash.AdmobSplashAdHelper.loadSplashAd(
                context = context,
                onAdLoaded = {
                    isAdLoading = false
                    io.module.ad.admob.splash.AdmobSplashAdHelper.showSplashAd(context)
                },
                onAdFailed = { _: Int, _: String ->
                    isAdLoading = false
                    onAdClosed.invoke()
                },
                onAdClosed = {
                    onAdClosed.invoke()
                }
            )
        }
    }
}