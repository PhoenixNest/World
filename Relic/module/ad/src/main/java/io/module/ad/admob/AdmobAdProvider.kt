package io.module.ad.admob

import android.app.Activity
import android.content.Context
import android.view.ViewGroup
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.appopen.AppOpenAd.AppOpenAdLoadCallback
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import io.common.util.LogUtil
import io.module.ad.BuildConfig
import io.module.ad.admob.utils.AdmobAdType
import io.module.ad.admob.utils.AdmobAdUnitId
import io.module.ad.core.model.AdInfoWrapper
import io.module.ad.core.provider.AbsAdProvider
import io.module.ad.core.provider.IAdListener

/**
 * Provide the core function of ad load and show.
 *
 * @see io.dev.relic.core.sdk.ad.core.AdmobAdManager
 * */
object AdmobAdProvider : AbsAdProvider() {

    private const val TAG = "AdmobAdProvider"

    /**
     * Initialize the ad-sdk or do some pre action.
     *
     * @param context
     * */
    override fun init(context: Context) {
        MobileAds.initialize(context)
    }

    /**
     * Load ad with the specified adUnitId.
     *
     * @param context
     * @param adUnitId
     * @param adType
     * @param adViewContainer
     * */
    override fun loadAd(
        context: Context,
        adUnitId: String,
        adType: String,
        adViewContainer: ViewGroup?
    ) {
        val targetAdUnitId: String = if (BuildConfig.NO_ADS) {
            LogUtil.warning(TAG, "[Load Ad] Enable No-Ads mode.")
            AdmobAdUnitId.NO_AD
        } else {
            adUnitId
        }

        loadAdmobAd(
            context = context,
            adUnitId = targetAdUnitId,
            adType = adType,
            adViewContainer = adViewContainer
        )
    }

    /**
     * Display ad with the specified adUnitId when loaded.
     *
     * @param context
     * @param adUnitId
     * @param adViewContainer
     * */
    override fun showAd(
        context: Context,
        adUnitId: String,
        adViewContainer: ViewGroup?
    ) {
        showAdmobAd(
            context = context,
            adObject = getAdInfoWrapper(adUnitId)?.adObject,
            adUnitId = adUnitId,
            listener = getAdListener(adUnitId),
            adViewContainer = adViewContainer
        )
    }

    /* ======================== load ======================== */

    /**
     * Load ad with the specified adType.
     *
     * @param context
     * @param adUnitId
     * @param adType            Such as: Open-Ad, Reward-Ad, etc.
     * @param adViewContainer   Some ad type need the host to display ad, such as: Banner-Ad, Native-Ad, etc.
     * */
    private fun loadAdmobAd(
        context: Context,
        adUnitId: String,
        @AdmobAdType adType: String,
        adViewContainer: ViewGroup?
    ) {
        when (adType) {
            AdmobAdType.OPEN_AD -> {
                loadAdmobOpenAd(
                    context = context,
                    adUnitId = adUnitId
                )
            }

            AdmobAdType.REWARD_AD -> {
                loadAdmobRewardAd(
                    context = context,
                    adUnitId = adUnitId
                )
            }

            AdmobAdType.INTERSTITIAL_AD -> {
                loadAdmobInterstitialAd(
                    context = context,
                    adUnitId = adUnitId
                )
            }

            AdmobAdType.BANNER_AD -> {
                loadAdmobBannerAd(
                    context = context,
                    adUnitId = adUnitId,
                    adViewContainer = adViewContainer
                )
            }

            AdmobAdType.NATIVE_AD -> {
                loadAdmobNativeAd(
                    context = context,
                    adUnitId = adUnitId,
                    adViewContainer = adViewContainer
                )
            }
        }
    }

    private fun loadAdmobOpenAd(
        context: Context,
        adUnitId: String
    ) {
        AppOpenAd.load(
            /* context = */ context,
            /* adUnitId = */ adUnitId,
            /* adRequest = */ AdRequest.Builder().build(),
            /* loadCallback = */ object : AppOpenAdLoadCallback() {
                override fun onAdFailedToLoad(error: LoadAdError) {
                    updateLoadStatus(
                        adUnitId = adUnitId,
                        isLoading = false
                    )
                    removeAdInfo(adUnitId)
                    getAdListener(adUnitId)?.onAdFailToLoad(
                        errorCode = error.code,
                        errorMessage = error.message
                    )
                }

                override fun onAdLoaded(appOpenAd: AppOpenAd) {
                    updateLoadStatus(
                        adUnitId = adUnitId,
                        isLoading = false
                    )
                    saveAdInfo(
                        adUnitId = adUnitId,
                        adInfoWrapper = AdInfoWrapper(appOpenAd)
                    )
                    getAdListener(adUnitId)?.onAdLoaded()
                }
            }
        )
    }

    private fun loadAdmobRewardAd(
        context: Context,
        adUnitId: String
    ) {
        RewardedAd.load(
            /* context = */ context,
            /* adUnitId = */ adUnitId,
            /* adRequest = */ AdRequest.Builder().build(),
            /* loadCallback = */ object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(error: LoadAdError) {
                    updateLoadStatus(
                        adUnitId = adUnitId,
                        isLoading = false
                    )
                    removeAdInfo(adUnitId)
                    getAdListener(adUnitId)?.onAdFailToLoad(
                        errorCode = error.code,
                        errorMessage = error.message
                    )
                }

                override fun onAdLoaded(rewardedAd: RewardedAd) {
                    updateLoadStatus(
                        adUnitId = adUnitId,
                        isLoading = false
                    )
                    saveAdInfo(
                        adUnitId = adUnitId,
                        adInfoWrapper = AdInfoWrapper(rewardedAd)
                    )
                    getAdListener(adUnitId)?.onAdLoaded()
                }
            }
        )
    }

    private fun loadAdmobInterstitialAd(
        context: Context,
        adUnitId: String
    ) {
        InterstitialAd.load(
            /* context = */ context,
            /* adUnitId = */ adUnitId,
            /* adRequest = */ AdRequest.Builder().build(),
            /* loadCallback = */ object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(error: LoadAdError) {
                    updateLoadStatus(
                        adUnitId = adUnitId,
                        isLoading = false
                    )
                    removeAdInfo(adUnitId)
                    getAdListener(adUnitId)?.onAdFailToLoad(
                        errorCode = error.code,
                        errorMessage = error.message
                    )
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    updateLoadStatus(
                        adUnitId = adUnitId,
                        isLoading = false
                    )
                    saveAdInfo(
                        adUnitId = adUnitId,
                        adInfoWrapper = AdInfoWrapper(interstitialAd)
                    )
                    getAdListener(adUnitId)?.onAdLoaded()
                }
            }
        )
    }

    /**
     * @see showAdmobNativeAd
     * */
    private fun loadAdmobBannerAd(
        context: Context,
        adUnitId: String,
        adViewContainer: ViewGroup?
    ) {
        showAdmobBannerAd(
            context = context,
            adUnitId = adUnitId,
            listener = getAdListener(adUnitId),
            adViewContainer = adViewContainer
        )
    }

    /**
     * @see showAdmobNativeAd
     * */
    private fun loadAdmobNativeAd(
        context: Context,
        adUnitId: String,
        adViewContainer: ViewGroup?
    ) {
        showAdmobNativeAd(
            context = context,
            adUnitId = adUnitId,
            listener = getAdListener(adUnitId),
            adViewContainer = adViewContainer
        )
    }

    /* ======================== show ======================== */

    private fun showAdmobAd(
        context: Context,
        adObject: Any?,
        adUnitId: String,
        listener: IAdListener?,
        adViewContainer: ViewGroup?
    ) {
        when (adObject) {

            is AppOpenAd -> {
                showAdmobOpenAd(
                    context = context,
                    openAd = adObject,
                    adUnitId = adUnitId,
                    listener = listener
                )
            }

            is RewardedAd -> {
                showAdmobRewardAd(
                    context = context,
                    rewardAd = adObject,
                    adUnitId = adUnitId,
                    listener = listener
                )
            }

            is InterstitialAd -> {
                showAdmobInterstitialAd(
                    context = context,
                    interstitialAd = adObject,
                    adUnitId = adUnitId,
                    listener = listener
                )
            }

            is AdView -> {
                showAdmobBannerAd(
                    context = context,
                    adUnitId = adUnitId,
                    listener = listener,
                    adViewContainer = adViewContainer
                )
            }

            is NativeAd -> {
                showAdmobNativeAd(
                    context = context,
                    adUnitId = adUnitId,
                    listener = listener,
                    adViewContainer = adViewContainer
                )
            }
        }
    }

    /* ======================== show ======================== */

    private fun showAdmobOpenAd(
        context: Context,
        openAd: AppOpenAd,
        adUnitId: String,
        listener: IAdListener?
    ) {
        openAd.apply {
            fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdClicked() {
                    listener?.onAdClicked()
                }

                override fun onAdDismissedFullScreenContent() {
                    listener?.onAdClosed()
                    removeAdInfo(adUnitId)
                }

                override fun onAdFailedToShowFullScreenContent(error: AdError) {
                    removeAdInfo(adUnitId)
                    listener?.onAdFailToShow(
                        errorCode = error.code,
                        errorMessage = error.message
                    )
                }

                override fun onAdShowedFullScreenContent() {
                    listener?.onAdShowed()
                }
            }
        }.run {
            show(context as Activity)
        }
    }

    private fun showAdmobRewardAd(
        context: Context,
        rewardAd: RewardedAd,
        adUnitId: String,
        listener: IAdListener?
    ) {
        rewardAd.apply {
            fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdClicked() {
                    listener?.onAdClicked()
                }

                override fun onAdDismissedFullScreenContent() {
                    listener?.onAdClosed()
                    removeAdInfo(adUnitId)
                }

                override fun onAdFailedToShowFullScreenContent(error: AdError) {
                    removeAdInfo(adUnitId)
                    listener?.onAdFailToShow(
                        errorCode = error.code,
                        errorMessage = error.message
                    )
                }

                override fun onAdShowedFullScreenContent() {
                    listener?.onAdShowed()
                }
            }
        }.run {
            show(context as Activity) {
                listener?.onAdEarnedReward()
            }
        }
    }

    private fun showAdmobInterstitialAd(
        context: Context,
        interstitialAd: InterstitialAd,
        adUnitId: String,
        listener: IAdListener?
    ) {
        interstitialAd.apply {
            fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdClicked() {
                    listener?.onAdClicked()
                }

                override fun onAdDismissedFullScreenContent() {
                    removeAdInfo(adUnitId)
                    listener?.onAdClosed()
                }

                override fun onAdFailedToShowFullScreenContent(error: AdError) {
                    removeAdInfo(adUnitId)
                    listener?.onAdFailToShow(
                        errorCode = error.code,
                        errorMessage = error.message
                    )
                }

                override fun onAdShowedFullScreenContent() {
                    listener?.onAdShowed()
                }
            }
        }.run {
            show(context as Activity)
        }
    }

    private fun showAdmobBannerAd(
        context: Context,
        adUnitId: String,
        listener: IAdListener?,
        adViewContainer: ViewGroup?
    ) {
        if (adViewContainer != null) {
            (adViewContainer as AdView).apply {
                setAdUnitId(adUnitId)
                setAdSize(AdSize.BANNER)
                loadAd(AdRequest.Builder().build())
                adListener = object : AdListener() {
                    override fun onAdClicked() {
                        listener?.onAdClicked()
                    }

                    override fun onAdClosed() {
                        removeAdInfo(adUnitId)
                        listener?.onAdClosed()
                    }

                    override fun onAdFailedToLoad(error: LoadAdError) {
                        removeAdInfo(adUnitId)
                        listener?.onAdFailToLoad(
                            errorCode = error.code,
                            errorMessage = error.message
                        )
                    }

                    override fun onAdLoaded() {
                        listener?.onAdLoaded()
                    }

                    override fun onAdOpened() {
                        listener?.onAdShowed()
                    }
                }
            }
        }
    }

    private fun showAdmobNativeAd(
        context: Context,
        adUnitId: String,
        listener: IAdListener?,
        adViewContainer: ViewGroup?
    ) {
        AdLoader.Builder(
            /* context = */ context,
            /* adUnitID = */ adUnitId
        ).forNativeAd { nativeAd: NativeAd ->
            if (adViewContainer != null) {
                (adViewContainer as NativeAdView).apply {
                    setNativeAd(nativeAd)
                }
            }
        }.withAdListener(
            object : AdListener() {
                override fun onAdClicked() {
                    listener?.onAdClicked()
                }

                override fun onAdClosed() {
                    listener?.onAdClosed()
                    removeAdInfo(adUnitId)
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    removeAdInfo(adUnitId)
                    listener?.onAdFailToLoad(
                        errorCode = error.code,
                        errorMessage = error.message
                    )
                }

                override fun onAdLoaded() {
                    listener?.onAdLoaded()
                }

                override fun onAdOpened() {
                    listener?.onAdShowed()
                }
            }
        ).build()
    }
}