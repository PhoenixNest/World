package io.dev.relic.global

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {

    companion object {

        private const val TAG = "MyApplication"

        lateinit var myApplication: MyApplication

        fun getInstance(): MyApplication {
            return myApplication
        }

        fun getApplicationContext(): Context {
            return myApplication.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        myApplication = this
    }

}