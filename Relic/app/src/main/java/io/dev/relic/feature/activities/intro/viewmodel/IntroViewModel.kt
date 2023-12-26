package io.dev.relic.feature.activities.intro.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.PeriodicWorkRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import io.common.RelicWorkerSystem
import io.common.work.CpuWorker
import io.core.datastore.RelicDatastoreCenter.writeSyncData
import io.core.datastore.preference_keys.UserPreferenceKeys.KEY_IS_AGREE_USER_PRIVACY
import io.core.datastore.preference_keys.UserPreferenceKeys.KEY_IS_AGREE_USER_TERMS
import io.core.datastore.preference_keys.UserPreferenceKeys.KEY_IS_SHOW_USER_AGREEMENT
import io.dev.relic.global.RelicSdkManager.initSdk
import javax.inject.Inject

@HiltViewModel
class IntroViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    private val permissionLiveData: MutableLiveData<Boolean> = MutableLiveData(false)

    companion object {
        private const val TAG = "IntroViewModel"
    }

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

    fun initRequiredAppComponent(context: Context) {
        initSdk(context)
        startWorkerRequest(context)
    }

    private fun startWorkerRequest(context: Context) {
        val cpuWorkerRequest: PeriodicWorkRequest = CpuWorker.buildRequest()
        RelicWorkerSystem.enqueueWorker(context, cpuWorkerRequest)
    }
}