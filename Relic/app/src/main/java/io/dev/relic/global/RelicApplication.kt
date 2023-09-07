package io.dev.relic.global

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import io.dev.relic.core.sdk.RelicSdkManager

@HiltAndroidApp
class RelicApplication : Application() {

    companion object {

        private const val TAG = "RelicApplication"

        lateinit var relicApplication: RelicApplication

        fun getInstance(): RelicApplication {
            return relicApplication
        }

        fun getApplicationContext(): Context {
            return relicApplication.applicationContext
        }
    }

    /* ======================== Logical ======================== */

    override fun onCreate() {
        super.onCreate()
        relicApplication = this

        // Initializes the global lifecycle observer.
        initLifecycleObserver()

        // Initializes the app's required sdk.
        initSdk(this)
    }

    private fun initLifecycleObserver() {
        RelicLifecycleObserver.init()
    }

    private fun initSdk(context: Context) {
        RelicSdkManager.initSdk(context)
    }

}