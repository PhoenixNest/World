package io.dev.relic.core.sdk.ad.provider.core

import io.dev.relic.core.sdk.ad.model.AdInfoWrapper
import io.dev.relic.global.utils.LogUtil
import io.dev.relic.global.utils.TimeUtil
import java.util.concurrent.TimeUnit

abstract class AbsAdProvider : IAdProvider {

    /**
     * Map used to store the current advertising information.
     * */
    private val adInfoMap: MutableMap<String, AdInfoWrapper> = mutableMapOf()

    /**
     * Map used to store the current Ad load listener.
     * */
    private val adListenerMap: MutableMap<String, IAdListener> = mutableMapOf()

    /**
     * Map to store the current Ad load status.
     * */
    private val adLoadStatusMap: MutableMap<String, Boolean> = mutableMapOf()

    companion object {
        private const val TAG = "AbsAdProvider"
    }

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
     * @param value         New status
     * */
    fun updateLoadStatus(
        adUnitId: String,
        value: Boolean
    ) {
        adLoadStatusMap[adUnitId] = value
    }

    /**
     * Check whether the Ad for the specified location can currently be loaded.
     *
     * @param adUnitId
     * */
    fun canLoadAd(adUnitId: String): Boolean {
        getCacheAdInfoWrapper(adUnitId)?.run {
            adListenerMap[adUnitId]?.onAdLoad()
            return false
        }

        if (isAdLoading(adUnitId)) {
            return false
        }

        updateLoadStatus(adUnitId, true)
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
        adInfoMap[adUnitId] = adInfoWrapper
    }

    /**
     * Remove Ad with the specified Ad Id.
     *
     * @param adUnitId
     * */
    fun removeAdInfo(adUnitId: String) {
        LogUtil.debug(TAG, "[Remove ad info] adUnitId: $adUnitId")
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
            val interval: Long = TimeUtil.getCurrentTimeInMillis() - adInfoWrapper.timeStamp
            val hasExpired: Boolean = TimeUnit.MICROSECONDS.toHours(interval) > 2
            if (hasExpired) {
                removeAdInfo(adUnitId)
                null
            } else {
                adInfoWrapper
            }
        }
    }

}