package io.module.ad.admob.splash

import android.content.Context
import io.module.ad.admob.AdmobAdManager
import io.module.ad.admob.utils.AdmobAdConfig
import io.module.ad.core.AdErrorCode
import io.module.ad.core.provider.IAdListener
import io.module.ad.utils.AdLogUtil
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.coroutines.resume

object AdmobSplashAdHelper {

    private const val TAG = "${AdmobAdConfig.TAG}_Splash"

    /**
     * Load a Splash-Ad.
     *
     * @param context
     * @param onAdLoaded
     * @param onAdClosed
     * @param onAdFailed
     *
     * @see loadAd
     * @see showSplashAd
     * */
    suspend fun loadSplashAd(
        context: Context,
        onAdLoaded: () -> Unit,
        onAdFailed: (errorCode: Int, errorMessage: String) -> Unit,
        onAdClosed: () -> Unit
    ) {
        AdLogUtil.d(TAG, "[Load-Ad] Loading...")
        val isTimeout = loadAd(
            context = context,
            onAdLoaded = onAdLoaded,
            onAdFailed = onAdFailed,
            onAdClosed = onAdClosed
        )

        if (isTimeout == null) {
            AdLogUtil.d(TAG, "[Admob | Open-Ad] Server Timeout.")
            onAdFailed.invoke(AdErrorCode.TIMEOUT, "Server Timeout.")
        }
    }

    /**
     * Display the loaded Splash-Ad.
     *
     * @param context
     *
     * @see loadSplashAd
     * */
    fun showSplashAd(context: Context) {
        AdLogUtil.d(TAG, "[Admob | Open-Ad] Display Ad.")
        AdmobAdManager.showOpenAd(context)
    }

    private suspend fun loadAd(
        context: Context,
        onAdLoaded: () -> Unit,
        onAdFailed: (errorCode: Int, errorMessage: String) -> Unit,
        onAdClosed: () -> Unit
    ): Boolean? {
        return withTimeoutOrNull(AdmobAdConfig.OPEN_AD_TIMEOUT_DURATION) {
            return@withTimeoutOrNull suspendCancellableCoroutine {
                AdmobAdManager.loadOpenAd(
                    context = context,
                    listener = object : IAdListener {

                        /**
                         * Callback when ad has already loaded.
                         * */
                        override fun onAdLoaded() {
                            AdLogUtil.d(TAG, "[Load-Ad] onAdLoaded")
                            onAdLoaded.invoke()
                            it.resume(true)
                        }

                        /**
                         * Callback when ad fail to load.
                         *
                         * @param errorCode
                         * @param errorMessage
                         * */
                        override fun onAdFailToLoad(errorCode: Int, errorMessage: String) {
                            AdLogUtil.e(TAG, "[Load-Ad] onAdFailToLoad($errorCode, $errorMessage)")
                            onAdFailed.invoke(errorCode, errorMessage)
                            it.resume(false)
                        }

                        /**
                         * Callback when ad fail to show.
                         *
                         * @param errorCode
                         * @param errorMessage
                         * */
                        override fun onAdFailToShow(errorCode: Int, errorMessage: String) {
                            AdLogUtil.e(TAG, "[Load-Ad] onAdFailToShow($errorCode, $errorMessage)")
                            onAdFailed.invoke(errorCode, errorMessage)
                            it.resume(false)
                        }

                        /**
                         * Callback when ad has already showed.
                         * */
                        override fun onAdShowed() {
                            AdLogUtil.d(TAG, "[Display-Ad] onAdShowed")
                        }

                        /**
                         * Callback when user has performed the click action.
                         * */
                        override fun onAdClicked() {
                            AdLogUtil.w(TAG, "[Click-Ad] onAdClicked")
                        }

                        /**
                         * Callback when user has closed the current showed ad.
                         * */
                        override fun onAdClosed() {
                            AdLogUtil.d(TAG, "[Close-Ad] onAdClosed")
                            onAdClosed.invoke()
                        }

                        /**
                         * Callback when user has finished and earned reward from the last ad process.
                         * */
                        override fun onAdEarnedReward() {
                            //
                        }
                    }
                )
            }
        }
    }
}