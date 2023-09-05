package io.dev.relic.core.sdk.ad

import android.content.Context
import android.view.View
import android.view.ViewGroup
import io.dev.relic.core.sdk.ad.provider.AdmobAdProvider
import io.dev.relic.core.sdk.ad.provider.core.IAdListener
import io.dev.relic.core.sdk.ad.utils.AdmobAdType
import io.dev.relic.global.RelicLifecycleObserver

/**
 * [Google • Admob](https://developers.google.cn/admob/android/quick-start)
 * */
object AdmobAdManager {

    fun init(context: Context) {
        AdmobAdProvider.init(context)
    }

    /* ======================== load ======================== */

    fun loadOpenAd(
        context: Context,
        listener: IAdListener
    ) {
        AdmobAdProvider.apply {
            setAdListener(
                adUnitId = "",
                listener = listener
            )
        }.run {
            loadAd(
                context = context,
                adUnitId = "",
                adType = AdmobAdType.OPEN_AD,
                adViewContainer = null
            )
        }
    }

    fun loadRewardAd(
        context: Context,
        listener: IAdListener
    ) {
        AdmobAdProvider.apply {
            setAdListener(
                adUnitId = "",
                listener = listener
            )
        }.run {
            loadAd(
                context = context,
                adUnitId = "",
                adType = AdmobAdType.REWARD_AD,
                adViewContainer = null
            )
        }
    }

    fun loadInterstitialAd(
        context: Context,
        listener: IAdListener
    ) {
        AdmobAdProvider.apply {
            setAdListener(
                adUnitId = "",
                listener = listener
            )
        }.run {
            loadAd(
                context = context,
                adUnitId = "",
                adType = AdmobAdType.INTERSTITIAL_AD,
                adViewContainer = null
            )
        }
    }

    fun loadBannerAd(
        context: Context,
        listener: IAdListener
    ) {
        AdmobAdProvider.apply {
            setAdListener(
                adUnitId = "",
                listener = listener
            )
        }.run {
            loadAd(
                context = context,
                adUnitId = "",
                adType = AdmobAdType.BANNER_AD,
                adViewContainer = null
            )
        }
    }

    fun loadNativeAd(
        context: Context,
        listener: IAdListener
    ) {
        AdmobAdProvider.apply {
            setAdListener(
                adUnitId = "",
                listener = listener
            )
        }.run {
            loadAd(
                context = context,
                adUnitId = "",
                adType = AdmobAdType.NATIVE_AD,
                adViewContainer = null
            )
        }
    }

    /* ======================== show ======================== */

    fun showOpenAd(
        context: Context,
        adUnitId: String
    ) {
        AdmobAdProvider.showAd(
            context = context,
            adUnitId = adUnitId
        )
    }

    fun showRewardAd(
        context: Context,
        adUnitId: String,
        ifInBackground: (() -> Unit)? = null
    ) {
        RelicLifecycleObserver.runOnForeground(
            foregroundAction = {
                //
            },
            backgroundAction = {
                ifInBackground?.invoke()
            }
        )
    }

    fun showInterstitialAd(
        context: Context,
        adUnitId: String
    ) {
        RelicLifecycleObserver.runOnForeground {
            AdmobAdProvider.showAd(
                context = context,
                adUnitId = adUnitId
            )
        }
    }

    fun showBannerAd(
        context: Context,
        adUnitId: String,
        adViewContainer: ViewGroup
    ) {
        AdmobAdProvider.showAd(
            context = context,
            adUnitId = adUnitId,
            adViewContainer = adViewContainer
        )
    }

    fun showNativeAd(
        context: Context,
        adUnitId: String,
        adViewContainer: ViewGroup
    ) {
        AdmobAdProvider.showAd(
            context = context,
            adUnitId = adUnitId,
            adViewContainer = adViewContainer
        )
    }
}