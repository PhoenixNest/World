package io.dev.relic.feature.activities.splash.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.common.util.LogUtil
import io.dev.relic.global.RelicLifecycleObserver
import io.module.ad.admob.splash.AdmobSplashAdHelper
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    /**
     * Indicate the loading status of the Splash-Ad
     * */
    private var isAdLoading = false

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
            LogUtil.w(TAG, "[Load Splash-Ad] No need to request the Ad if the app is launched for the first time")
            onAdClosed.invoke()
            return
        }

        // If the Ad is currently loading, there is no need to request the Ad twice.
        if (isAdLoading) {
            LogUtil.w(TAG, "[Load Splash-Ad] No need to request the Ad twice")
            return
        }

        viewModelScope.launch {
            isAdLoading = true
            AdmobSplashAdHelper.loadSplashAd(
                context = context,
                onAdLoaded = {
                    isAdLoading = false
                    AdmobSplashAdHelper.showSplashAd(context)
                },
                onAdFailed = { _, _ ->
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