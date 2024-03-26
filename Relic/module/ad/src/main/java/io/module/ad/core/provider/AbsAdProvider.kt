package io.module.ad.core.provider

import io.module.ad.utils.AdLogUtil
import io.module.ad.utils.AdTimeUtil
import io.module.ad.core.AdConfig
import io.module.ad.core.model.AdInfoWrapper
import java.util.concurrent.TimeUnit

abstract class AbsAdProvider : IAdProvider {

    /**
     * Map used to store the current advertising information.
     * */
    private val adInfoMap = mutableMapOf<String, AdInfoWrapper>()

    /**
     * Map used to store the current Ad load listener.
     * */
    private val adListenerMap = mutableMapOf<String, IAdListener>()

    /**
     * Map to store the current Ad load status.
     * */
    private val adLoadStatusMap = mutableMapOf<String, Boolean>()

    companion object {
        private const val TAG = "AbsAdProvider"
    }

    /**
     * Binds the callback listener to Ad instance with the specified adUnitId.
     *
     * @param adUnitId
     * @param listener
     * */
    override fun setAdListener(adUnitId: String, listener: IAdListener?) {
        if (listener == null) {
            adListenerMap.remove(adUnitId)
        } else {
            adListenerMap[adUnitId] = listener
        }
    }

    /**
     * Get the current ad listener by adUnitId.
     *
     * @param adUnitId
     * */
    fun getAdListener(adUnitId: String): IAdListener? {
        return adListenerMap[adUnitId]
    }

    /**
     * Get the instance of ad wrapper.
     *
     * @param adUnitId
     * @see AdInfoWrapper
     * */
    fun getAdInfoWrapper(adUnitId: String): AdInfoWrapper? {
        return adInfoMap[adUnitId]
    }

    /**
     * Get the instance of ad object.
     *
     * @param adUnitId
     */
    fun getAdObject(adUnitId: String): Any? {
        return adInfoMap[adUnitId]?.adObject
    }

    /**
     * Update the current loading status of ad by adUnitId.
     *
     * @param adUnitId
     * @param isLoading
     * */
    fun updateLoadStatus(
        adUnitId: String,
        isLoading: Boolean
    ) {
        adLoadStatusMap[adUnitId] = isLoading
    }

    /**
     * Check whether the Ad for the specified location can currently be loaded.
     *
     * @param adUnitId
     * */
    fun canLoadAd(adUnitId: String): Boolean {
        getCacheAdInfoWrapper(adUnitId)?.run {
            adListenerMap[adUnitId]?.onAdLoaded()
            return false
        }

        if (isAdLoading(adUnitId)) {
            return false
        }

        updateLoadStatus(
            adUnitId = adUnitId,
            isLoading = true
        )
        return true
    }

    /**
     * Save cache ads.
     *
     * @param adUnitId
     * @param adInfoWrapper
     * */
    fun saveAdInfo(
        adUnitId: String,
        adInfoWrapper: AdInfoWrapper
    ) {
        AdLogUtil.d(TAG, "[Save ad info] AdInfoWrapper: $adInfoWrapper")
        adInfoMap[adUnitId] = adInfoWrapper
    }

    /**
     * Remove Ad with the specified Ad Id.
     *
     * @param adUnitId
     * */
    fun removeAdInfo(adUnitId: String) {
        AdLogUtil.d(TAG, "[Remove ad info] adUnitId: $adUnitId")
        adInfoMap.remove(adUnitId)
    }

    /**
     * Check if the current Ad is loading.
     *
     * @param adUnitId
     * */
    private fun isAdLoading(adUnitId: String): Boolean {
        if (!adLoadStatusMap.containsKey(adUnitId)) {
            adLoadStatusMap[adUnitId] = false
        }

        return adLoadStatusMap[adUnitId] == true
    }

    /**
     * Reads the current AdInfoWrapper from the cache.
     *
     * @param adUnitId
     * */
    private fun getCacheAdInfoWrapper(adUnitId: String): AdInfoWrapper? {
        return adInfoMap[adUnitId]?.let { adInfoWrapper: AdInfoWrapper ->
            val interval = (AdTimeUtil.getCurrentTimeInMillis() - adInfoWrapper.timeStamp)
            val interval2Hours = TimeUnit.MICROSECONDS.toHours(interval)
            val hasExpired = (interval2Hours > AdConfig.EXPIRED_DURATION)
            AdLogUtil.d(TAG, "[Check ad has expired] hasExpired: $hasExpired")

            if (hasExpired) {
                removeAdInfo(adUnitId)
                null
            } else {
                adInfoWrapper
            }
        }
    }
}