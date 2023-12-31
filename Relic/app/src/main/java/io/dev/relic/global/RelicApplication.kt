package io.dev.relic.global

import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import io.common.app.BaseApplication

@HiltAndroidApp
class RelicApplication : BaseApplication() {

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
    }

    private fun initLifecycleObserver() {
        RelicLifecycleObserver.init()
    }
}