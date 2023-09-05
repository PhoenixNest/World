package io.dev.relic.core.sdk.ad.provider.core

import android.content.Context
import android.view.ViewGroup

interface IAdProvider {

    fun init(context: Context)

    fun loadAd(
        context: Context,
        adUnitId: String,
        adType: String,
        adViewContainer: ViewGroup? = null
    )

    fun setAdListener(
        adUnitId: String,
        listener: IAdListener?
    )

    fun showAd(
        context: Context,
        adUnitId: String,
        adViewContainer: ViewGroup? = null
    )

}