package io.dev.relic.core.sdk

import android.content.Context
import io.dev.relic.core.sdk.ad.admob.AdmobAdManager

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