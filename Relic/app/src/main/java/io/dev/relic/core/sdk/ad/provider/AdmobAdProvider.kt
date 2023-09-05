package io.dev.relic.core.sdk.ad.provider

import android.content.Context
import android.view.ViewGroup
import com.google.android.gms.ads.MobileAds
import io.dev.relic.core.sdk.ad.provider.core.AbsAdProvider
import io.dev.relic.core.sdk.ad.utils.AdmobAdType

object AdmobAdProvider : AbsAdProvider() {

    override fun init(context: Context) {
        initAdmobSdk(context)
    }

    /* ======================== Override ======================== */

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
            adUnitId = adUnitId,
            adViewContainer = adViewContainer
        )
    }

    /* ======================== Public ======================== */

    /* ======================== Private ======================== */

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

            }

            AdmobAdType.REWARD_AD -> {

            }

            AdmobAdType.INTERSTITIAL_AD -> {

            }

            AdmobAdType.BANNER_AD -> {

            }

            AdmobAdType.NATIVE_AD -> {

            }
        }
    }

    private fun showAdmobAd(
        context: Context,
        adUnitId: String,
        adViewContainer: ViewGroup?
    ) {

    }
}