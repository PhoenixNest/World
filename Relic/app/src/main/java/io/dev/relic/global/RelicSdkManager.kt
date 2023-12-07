package io.dev.relic.global

import android.content.Context
import io.module.ad.admob.AdmobAdManager

object RelicSdkManager {

    /**
     * Initialize global sdk.
     * */
    fun initSdk(context: Context) {
        initAdmob(context)
    }

    /**
     * Initialize the admob sdk.
     *
     * @param context
     * */
    private fun initAdmob(context: Context) {
        AdmobAdManager.init(context)
    }

}