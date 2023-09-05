package io.dev.relic.core.sdk.ad.provider

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
import io.dev.relic.core.sdk.ad.model.AdInfoWrapper
import io.dev.relic.core.sdk.ad.provider.core.AbsAdProvider
import io.dev.relic.core.sdk.ad.provider.core.IAdListener
import io.dev.relic.core.sdk.ad.utils.AdmobAdType

object AdmobAdProvider : AbsAdProvider() {

    override fun init(context: Context) {
        initAdmobSdk(context)
    }

    override fun loadAd(
        context: Context,
        adUnitId: String,
        adType: String,
        adViewContainer: ViewGroup?
    ) {
        loadAdmobAd(
            context = context,
            adUnitId = adUnitId,
            adType = adType,
            adViewContainer = adViewContainer
        )
    }

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

    private fun initAdmobSdk(context: Context) {
        MobileAds.initialize(context)
    }

    private fun loadAdmobAd(
        context: Context,
        adUnitId: String,
        @AdmobAdType adType: String,
        adViewContainer: ViewGroup?
    ) {
        when (adType) {
            AdmobAdType.OPEN_AD -> {
                loadAdmobOpenAd(context, adUnitId)
            }

            AdmobAdType.REWARD_AD -> {
                loadAdmobRewardAd(context, adUnitId)
            }

            AdmobAdType.INTERSTITIAL_AD -> {
                loadAdmobInterstitialAd(context, adUnitId)
            }

            AdmobAdType.BANNER_AD -> {
                loadAdmobBannerAd(context, adUnitId, adViewContainer)
            }

            AdmobAdType.NATIVE_AD -> {
                loadAdmobNativeAd(context, adUnitId, adViewContainer)
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
                    super.onAdFailedToLoad(error)
                    getAdListener(adUnitId)?.onAdFailToLoad(
                        errorCode = error.code,
                        errorMessage = error.message
                    )
                    updateLoadStatus(
                        adUnitId = adUnitId,
                        isLoading = false
                    )
                    removeAdInfo(adUnitId)
                }

                override fun onAdLoaded(appOpenAd: AppOpenAd) {
                    super.onAdLoaded(appOpenAd)
                    getAdListener(adUnitId)?.onAdLoaded()
                    updateLoadStatus(
                        adUnitId = adUnitId,
                        isLoading = false
                    )
                    saveAdInfo(
                        adUnitId = adUnitId,
                        adInfoWrapper = AdInfoWrapper(appOpenAd)
                    )
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
                    super.onAdFailedToLoad(error)
                    getAdListener(adUnitId)?.onAdFailToLoad(
                        errorCode = error.code,
                        errorMessage = error.message
                    )
                    updateLoadStatus(
                        adUnitId = adUnitId,
                        isLoading = false
                    )
                    removeAdInfo(adUnitId)
                }

                override fun onAdLoaded(rewardedAd: RewardedAd) {
                    super.onAdLoaded(rewardedAd)
                    getAdListener(adUnitId)?.onAdLoaded()
                    updateLoadStatus(
                        adUnitId = adUnitId,
                        isLoading = false
                    )
                    saveAdInfo(
                        adUnitId = adUnitId,
                        adInfoWrapper = AdInfoWrapper(rewardedAd)
                    )
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
                    super.onAdFailedToLoad(error)
                    getAdListener(adUnitId)?.onAdFailToLoad(
                        errorCode = error.code,
                        errorMessage = error.message
                    )
                    updateLoadStatus(
                        adUnitId = adUnitId,
                        isLoading = false
                    )
                    removeAdInfo(adUnitId)
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    super.onAdLoaded(interstitialAd)
                    getAdListener(adUnitId)?.onAdLoaded()
                    updateLoadStatus(
                        adUnitId = adUnitId,
                        isLoading = false
                    )
                    saveAdInfo(
                        adUnitId = adUnitId,
                        adInfoWrapper = AdInfoWrapper(interstitialAd)
                    )
                }
            }
        )
    }

    private fun loadAdmobBannerAd(
        context: Context,
        adUnitId: String,
        adViewContainer: ViewGroup?
    ) {

    }

    private fun loadAdmobNativeAd(
        context: Context,
        adUnitId: String,
        adViewContainer: ViewGroup?
    ) {

    }

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

    private fun showAdmobOpenAd(
        context: Context,
        openAd: AppOpenAd,
        adUnitId: String,
        listener: IAdListener?
    ) {
        openAd.apply {
            fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdClicked() {
                    super.onAdClicked()
                    listener?.onAdClicked()
                }

                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()
                    listener?.onAdClosed()
                    removeAdInfo(adUnitId)
                }

                override fun onAdFailedToShowFullScreenContent(error: AdError) {
                    super.onAdFailedToShowFullScreenContent(error)
                    listener?.onAdFailToShow(
                        errorCode = error.code,
                        errorMessage = error.message
                    )
                    removeAdInfo(adUnitId)
                }

                override fun onAdShowedFullScreenContent() {
                    super.onAdShowedFullScreenContent()
                    listener?.onAdShowed()
                }
            }
        }.also {
            it.show(context as Activity)
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
                    super.onAdClicked()
                    listener?.onAdClicked()
                }

                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()
                    listener?.onAdClosed()
                    removeAdInfo(adUnitId)
                }

                override fun onAdFailedToShowFullScreenContent(error: AdError) {
                    super.onAdFailedToShowFullScreenContent(error)
                    listener?.onAdFailToShow(
                        errorCode = error.code,
                        errorMessage = error.message
                    )
                    removeAdInfo(adUnitId)
                }

                override fun onAdShowedFullScreenContent() {
                    super.onAdShowedFullScreenContent()
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
                    super.onAdClicked()
                    listener?.onAdClicked()
                }

                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()
                    listener?.onAdClosed()
                    removeAdInfo(adUnitId)
                }

                override fun onAdFailedToShowFullScreenContent(error: AdError) {
                    super.onAdFailedToShowFullScreenContent(error)
                    listener?.onAdFailToShow(
                        errorCode = error.code,
                        errorMessage = error.message
                    )
                    removeAdInfo(adUnitId)
                }

                override fun onAdShowedFullScreenContent() {
                    super.onAdShowedFullScreenContent()
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
                        super.onAdClicked()
                        listener?.onAdClicked()
                    }

                    override fun onAdClosed() {
                        super.onAdClosed()
                        listener?.onAdClosed()
                    }

                    override fun onAdFailedToLoad(error: LoadAdError) {
                        super.onAdFailedToLoad(error)
                        listener?.onAdFailToLoad(
                            errorCode = error.code,
                            errorMessage = error.message
                        )
                        removeAdInfo(adUnitId)
                    }

                    override fun onAdLoaded() {
                        super.onAdLoaded()
                        listener?.onAdLoaded()
                    }

                    override fun onAdOpened() {
                        super.onAdOpened()
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
                    super.onAdClicked()
                    listener?.onAdClicked()
                }

                override fun onAdClosed() {
                    super.onAdClosed()
                    listener?.onAdClosed()
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    super.onAdFailedToLoad(error)
                    listener?.onAdFailToLoad(
                        errorCode = error.code,
                        errorMessage = error.message
                    )
                    removeAdInfo(adUnitId)
                }

                override fun onAdLoaded() {
                    super.onAdLoaded()
                    listener?.onAdLoaded()
                }

                override fun onAdOpened() {
                    super.onAdOpened()
                    listener?.onAdShowed()
                }
            }
        ).build()
    }
}