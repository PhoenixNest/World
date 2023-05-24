package io.dev.relic.feature

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

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
    }

    private fun initLifecycleObserver() {
        RelicLifecycleObserver.init()
    }

}