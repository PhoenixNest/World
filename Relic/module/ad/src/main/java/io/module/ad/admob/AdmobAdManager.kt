package io.module.ad.admob

import android.content.Context
import android.view.ViewGroup
import io.module.ad.admob.utils.AdmobAdType
import io.module.ad.admob.utils.AdmobAdUnitId
import io.module.ad.core.provider.IAdListener

/**
 * [Google • Admob](https://developers.google.cn/admob/android/quick-start)
 *
 * @see AdmobAdProvider
 * */
object AdmobAdManager {

    fun init(context: Context) {
        AdmobAdProvider.init(context)
    }

    fun getAdListener(adUnitId: String): IAdListener? {
        return AdmobAdProvider.getAdListener(adUnitId)
    }

    /* ======================== load ======================== */

    fun loadOpenAd(
        context: Context,
        listener: IAdListener
    ) {
        val adUnitId = AdmobAdUnitId.OPEN_AD
        AdmobAdProvider.apply {
            setAdListener(
                adUnitId = adUnitId,
                listener = listener
            )
        }.run {
            loadAd(
                context = context,
                adUnitId = adUnitId,
                adType = AdmobAdType.OPEN_AD,
                adViewContainer = null
            )
        }
    }

    fun loadRewardAd(
        context: Context,
        listener: IAdListener
    ) {
        val adUnitId = AdmobAdUnitId.REWARD_AD
        AdmobAdProvider.apply {
            setAdListener(
                adUnitId = adUnitId,
                listener = listener
            )
        }.run {
            loadAd(
                context = context,
                adUnitId = adUnitId,
                adType = AdmobAdType.REWARD_AD,
                adViewContainer = null
            )
        }
    }

    fun loadInterstitialAd(
        context: Context,
        listener: IAdListener
    ) {
        val adUnitId = AdmobAdUnitId.INTERSTITIAL_AD
        AdmobAdProvider.apply {
            setAdListener(
                adUnitId = adUnitId,
                listener = listener
            )
        }.run {
            loadAd(
                context = context,
                adUnitId = adUnitId,
                adType = AdmobAdType.INTERSTITIAL_AD,
                adViewContainer = null
            )
        }
    }

    /**
     * @see showBannerAd
     * */
    fun loadBannerAd(
        context: Context,
        listener: IAdListener
    ) {
        val adUnitId = AdmobAdUnitId.BANNER_AD
        AdmobAdProvider.apply {
            setAdListener(
                adUnitId = adUnitId,
                listener = listener
            )
        }.run {
            loadAd(
                context = context,
                adUnitId = adUnitId,
                adType = AdmobAdType.BANNER_AD,
                adViewContainer = null
            )
        }
    }

    /**
     * @see showNativeAd
     * */
    fun loadNativeAd(
        context: Context,
        listener: IAdListener
    ) {
        val adUnitId = AdmobAdUnitId.NATIVE_AD
        AdmobAdProvider.apply {
            setAdListener(
                adUnitId = adUnitId,
                listener = listener
            )
        }.run {
            loadAd(
                context = context,
                adUnitId = adUnitId,
                adType = AdmobAdType.NATIVE_AD,
                adViewContainer = null
            )
        }
    }

    /* ======================== show ======================== */

    fun showOpenAd(context: Context) {
        val adUnitId = AdmobAdUnitId.OPEN_AD
        AdmobAdProvider.showAd(
            context = context,
            adUnitId = adUnitId
        )
    }

    fun showRewardAd(
        context: Context,
        ifInBackground: (() -> Unit)? = null
    ) {
        val adUnitId = AdmobAdUnitId.REWARD_AD
        // RelicLifecycleObserver.runOnForeground(
        //     foregroundAction = {
        //         AdmobAdProvider.showAd(
        //             context = context,
        //             adUnitId = adUnitId
        //         )
        //     },
        //     backgroundAction = {
        //         ifInBackground?.invoke()
        //     }
        // )
    }

    fun showInterstitialAd(
        context: Context,
        ifInBackground: (() -> Unit)? = null
    ) {
        val adUnitId = AdmobAdUnitId.INTERSTITIAL_AD
        // RelicLifecycleObserver.runOnForeground(
        //     foregroundAction = {
        //         AdmobAdProvider.showAd(
        //             context = context,
        //             adUnitId = adUnitId
        //         )
        //     },
        //     backgroundAction = {
        //         ifInBackground?.invoke()
        //     }
        // )
    }

    private fun showBannerAd(
        context: Context,
        adViewContainer: ViewGroup
    ) {
        val adUnitId = AdmobAdUnitId.BANNER_AD
        AdmobAdProvider.showAd(
            context = context,
            adUnitId = adUnitId,
            adViewContainer = adViewContainer
        )
    }

    private fun showNativeAd(
        context: Context,
        adViewContainer: ViewGroup
    ) {
        val adUnitId = AdmobAdUnitId.NATIVE_AD
        AdmobAdProvider.showAd(
            context = context,
            adUnitId = adUnitId,
            adViewContainer = adViewContainer
        )
    }
}