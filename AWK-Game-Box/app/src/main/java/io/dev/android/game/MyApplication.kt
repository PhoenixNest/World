package io.dev.android.game

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {

    companion object {

        private var INSTANCE: MyApplication? = null

        fun getInstance(): MyApplication {
            return INSTANCE ?: synchronized(this) {
                MyApplication()
            }.also {
                INSTANCE = it
            }
        }
    }
}