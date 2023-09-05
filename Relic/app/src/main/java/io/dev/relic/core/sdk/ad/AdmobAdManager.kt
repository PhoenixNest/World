package io.dev.relic.core.sdk.ad

import android.content.Context
import io.dev.relic.core.sdk.ad.provider.AdmobAdProvider
import io.dev.relic.core.sdk.ad.provider.core.IAdListener

/**
 * [Google • Admob](https://developers.google.cn/admob/android/quick-start)
 * */
object AdmobAdManager {

    fun init(context: Context) {
        AdmobAdProvider.init(context)
    }

    fun loadOpenAd(
        context: Context,
        listener: IAdListener
    ) {

    }

    fun loadRewardAd(
        context: Context,
        listener: IAdListener
    ) {

    }

    fun loadInterstitialAd(
        context: Context,
        listener: IAdListener
    ) {

    }

}