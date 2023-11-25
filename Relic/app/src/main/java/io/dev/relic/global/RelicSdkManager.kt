package io.dev.relic.global

import android.content.Context

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
        io.module.ad.admob.AdmobAdManager.init(context)
    }

}