package io.dev.relic.core.sdk.ad.provider.core

interface IAdListener {

    fun onAdLoaded()

    fun onAdFailToShow(
        errorCode: Int,
        errorMessage: String
    )

    fun onAdFailToLoad(
        errorCode: Int,
        errorMessage: String
    )

    fun onAdShowed()

    fun onAdClicked()

    fun onAdClosed()

    fun onAdEarnedReward()

}