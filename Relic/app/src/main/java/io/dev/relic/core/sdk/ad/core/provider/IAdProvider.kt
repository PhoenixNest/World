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
     * Load ad with the specified adUnitId.
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
     * Display ad with the specified adUnitId when loaded.
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
     * Binds the callback listener to Ad instance with the specified adUnitId.
     *
     * @param adUnitId
     * @param listener
     * */
    fun setAdListener(
        adUnitId: String,
        listener: IAdListener?
    )

}