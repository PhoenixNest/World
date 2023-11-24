package io.dev.relic.core.sdk.ad.admob.reward

import android.content.Context
import io.dev.relic.core.sdk.ad.admob.AdmobAdManager
import io.dev.relic.core.sdk.ad.admob.utils.AdmobAdConfig
import io.dev.relic.core.sdk.ad.admob.utils.AdmobAdUnitId
import io.dev.relic.core.sdk.ad.core.AdErrorCode
import io.dev.relic.core.sdk.ad.core.provider.IAdListener
import io.module.common.util.LogUtil
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.coroutines.resume

object AdmobRewardAdHelper {

    private const val TAG = "${AdmobAdConfig.TAG}_Reward"

    /**
     * Load a Reward-Ad.
     *
     * @param context
     * @param onAdLoaded
     * @param onAdFailed
     * @param onAdEarnedReward
     * @param onAdClose
     *
     * @see loadAd
     * @see showRewardAd
     * */
    suspend fun loadRewardAd(
        context: Context,
        onAdLoaded: () -> Unit,
        onAdFailed: (errorCode: Int, errorMessage: String) -> Unit,
        onAdEarnedReward: () -> Unit,
        onAdClose: (isEarnedReward: Boolean) -> Unit
    ) {
        LogUtil.debug(TAG, "[Load-Ad] Loading...")
        val isTimeout: Boolean? = loadAd(
            context = context,
            onAdLoaded = onAdLoaded,
            onAdFailed = onAdFailed,
            onAdEarnedReward = onAdEarnedReward,
            onAdClose = onAdClose
        )

        if (isTimeout == null) {
            LogUtil.debug(TAG, "[Admob | Reward-Ad] Server Timeout.")
            onAdFailed.invoke(AdErrorCode.TIMEOUT, "Server Timeout.")
        }
    }

    /**
     * Display the loaded Reward-Ad.
     *
     * @param context
     *
     * @see loadRewardAd
     * */
    fun showRewardAd(context: Context) {
        LogUtil.debug(TAG, "[Admob | Reward-Ad] Display Ad.")
        AdmobAdManager.showRewardAd(
            context = context,
            ifInBackground = {
                LogUtil.error(TAG, "[Admob | Reward-Ad] No ads are allowed in the background.")
                val listener: IAdListener? = AdmobAdManager.getAdListener(AdmobAdUnitId.REWARD_AD)
                listener?.onAdFailToShow(
                    errorCode = AdErrorCode.AD_SHOW_IN_BACKGROUND,
                    errorMessage = "No ads are allowed in the background."
                )
            }
        )
    }

    private suspend fun loadAd(
        context: Context,
        onAdLoaded: () -> Unit,
        onAdFailed: (errorCode: Int, errorMessage: String) -> Unit,
        onAdEarnedReward: () -> Unit,
        onAdClose: (isEarnedReward: Boolean) -> Unit
    ): Boolean? {
        return withTimeoutOrNull(AdmobAdConfig.REWARD_AD_TIME_DURATION) {
            return@withTimeoutOrNull suspendCancellableCoroutine {
                var isEarnedReward = false

                AdmobAdManager.loadRewardAd(
                    context = context,
                    listener = object : IAdListener {

                        /**
                         * Callback when ad has already loaded.
                         * */
                        override fun onAdLoaded() {
                            LogUtil.debug(TAG, "[Load-Ad] onAdLoaded")
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
                            LogUtil.error(TAG, "[Load-Ad] onAdFailToLoad($errorCode, $errorMessage)")
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
                            LogUtil.error(TAG, "[Load-Ad] onAdFailToShow($errorCode, $errorMessage)")
                            onAdFailed.invoke(errorCode, errorMessage)
                            it.resume(false)
                        }

                        /**
                         * Callback when ad has already showed.
                         * */
                        override fun onAdShowed() {
                            LogUtil.debug(TAG, "[Display-Ad] onAdShowed")
                        }

                        /**
                         * Callback when user has performed the click action.
                         * */
                        override fun onAdClicked() {
                            LogUtil.warning(TAG, "[Click-Ad] onAdClicked")
                        }

                        /**
                         * Callback when user has closed the current showed ad.
                         * */
                        override fun onAdClosed() {
                            LogUtil.debug(TAG, "[Close-Ad] onAdClosed, isEarnedReward: $isEarnedReward")
                            onAdClose.invoke(isEarnedReward)
                        }

                        /**
                         * Callback when user has finished and earned reward from the last ad process.
                         * */
                        override fun onAdEarnedReward() {
                            LogUtil.debug(TAG, "[Close-Ad] onAdEarnedReward")
                            onAdEarnedReward.invoke()
                            isEarnedReward = true
                        }
                    }
                )
            }
        }
    }

}