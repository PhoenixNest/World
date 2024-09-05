package io.common.app

import android.app.Application
import android.content.Context

open class BaseApplication : Application() {

    companion object {

        private const val TAG = "BaseApplication"

        lateinit var baseApplication: BaseApplication

        fun getInstance(): BaseApplication {
            return baseApplication
        }

        fun getApplicationContext(): Context {
            return baseApplication.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        baseApplication = this
    }

}