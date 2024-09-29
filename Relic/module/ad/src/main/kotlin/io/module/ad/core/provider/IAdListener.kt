package io.module.ad.core.provider

interface IAdListener {

    /**
     * Callback when ad has already loaded.
     * */
    fun onAdLoaded()

    /**
     * Callback when ad fail to load.
     *
     * @param errorCode
     * @param errorMessage
     * */
    fun onAdFailToLoad(
        errorCode: Int,
        errorMessage: String
    )

    /**
     * Callback when ad fail to show.
     *
     * @param errorCode
     * @param errorMessage
     * */
    fun onAdFailToShow(
        errorCode: Int,
        errorMessage: String
    )

    /**
     * Callback when ad has already showed.
     * */
    fun onAdShowed()

    /**
     * Callback when user has performed the click action.
     * */
    fun onAdClicked()

    /**
     * Callback when user has closed the current showed ad.
     * */
    fun onAdClosed()

    /**
     * Callback when user has finished and earned reward from the last ad process.
     * */
    fun onAdEarnedReward()

}