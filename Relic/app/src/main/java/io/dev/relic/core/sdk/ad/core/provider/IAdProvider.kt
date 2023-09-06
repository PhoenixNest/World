package io.dev.relic.core.sdk.ad.core.provider

import android.content.Context
import android.view.ViewGroup

interface IAdProvider {

    /**
     * Initialize the ad-sdk or do some pre action.
     *
     * @param context
     * */
    fun init(context: Context)

    /**
     * Load ad with specify adUnitId.
     *
     * @param context
     * @param adUnitId
     * @param adType
     * @param adViewContainer
     * */
    fun loadAd(
        context: Context,
        adUnitId: String,
        adType: String,
        adViewContainer: ViewGroup? = null
    )

    /**
     * Display ad with specify adUnitId when loaded.
     *
     * @param context
     * @param adUnitId
     * @param adViewContainer
     * */
    fun showAd(
        context: Context,
        adUnitId: String,
        adViewContainer: ViewGroup? = null
    )

    /**
     * Binds the callback listener to Ad instance with specify adUnitId.
     *
     * @param adUnitId
     * @param listener
     * */
    fun setAdListener(
        adUnitId: String,
        listener: IAdListener?
    )

}