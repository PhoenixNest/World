package io.dev.relic.global

import android.Manifest
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.agent.gemini.GeminiAgent
import io.agent.gemini.R
import io.common.RelicResCenter.getString
import io.common.RelicWorkerSystem
import io.common.work.CpuWorker
import io.core.datastore.RelicDatastoreCenter.readSyncData
import io.core.datastore.RelicDatastoreCenter.writeSyncData
import io.domain.preference_key.UserPreferenceKey.KEY_IS_AGREE_USER_PRIVACY
import io.domain.preference_key.UserPreferenceKey.KEY_IS_AGREE_USER_TERMS
import io.domain.preference_key.UserPreferenceKey.KEY_IS_SHOW_USER_AGREEMENT
import io.module.ad.admob.AdmobAdManager

object RelicSdkManager {

    private const val TAG = "RelicSdkManager"

    private val permissionLiveData = MutableLiveData<Boolean>()
    private val sdkInitStatsLiveData = MutableLiveData<Boolean>()

    val permissionList = listOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
    )

    fun updatePermissionLiveData(value: Boolean) {
        permissionLiveData.value = value
    }

    fun getPermissionLiveData(): LiveData<Boolean> {
        return permissionLiveData
    }

    fun updateUserAgreementMarker() {
        writeSyncData(KEY_IS_SHOW_USER_AGREEMENT, true)
        writeSyncData(KEY_IS_AGREE_USER_TERMS, true)
        writeSyncData(KEY_IS_AGREE_USER_PRIVACY, true)
    }

    fun getUserAgreementMarker(): Boolean {
        val isShowUserAgreement = readSyncData(KEY_IS_SHOW_USER_AGREEMENT, false)
        val isAgreeUserTerms = readSyncData(KEY_IS_AGREE_USER_TERMS, false)
        val isAgreeUserPrivacy = readSyncData(KEY_IS_AGREE_USER_PRIVACY, false)
        return isShowUserAgreement && isAgreeUserTerms && isAgreeUserPrivacy
    }

    fun initRequiredAppComponent(context: Context) {
        initSdk(context)
        startWorkerRequest(context)
        sdkInitStatsLiveData.value = true
    }

    fun getSdkInitStatusLiveData(): LiveData<Boolean> {
        return sdkInitStatsLiveData
    }

    /**
     * Initialize global sdk.
     * */
    private fun initSdk(context: Context) {
        initAdmob(context)
        initGemini()
    }

    /**
     * Initialize the admob sdk.
     *
     * @param context
     * */
    private fun initAdmob(context: Context) {
        AdmobAdManager.init(context)
    }

    /**
     * Initialize the gemini sdk.
     * */
    private fun initGemini() {
        val devKey = getString(R.string.agent_gemini_dev_key)
        GeminiAgent.initGeminiComponent(devKey)
    }

    /**
     * Enqueue your workers.
     *
     * @param context
     * */
    fun startWorkerRequest(context: Context) {
        // Cpu worker
        val cpuWorkerRequest = CpuWorker.buildPeriodRequest()
        RelicWorkerSystem.enqueueUniquePeriodWorker(context, CpuWorker.TAG, cpuWorkerRequest)
    }

}