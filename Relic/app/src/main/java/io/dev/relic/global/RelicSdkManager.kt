package io.dev.relic.global

import android.content.Context
import io.module.ad.admob.AdmobAdManager
import io.module.map.tomtom.TomTomMapManager

object RelicSdkManager {

    /**
     * Initialize global sdk.
     * */
    fun initSdk(context: Context) {
        initAdmob(context)
        initMapComponent()
    }

    /**
     * Initialize the admob sdk.
     *
     * @param context
     * */
    private fun initAdmob(context: Context) {
        AdmobAdManager.init(context)
    }

    /**
     * Initialize the map component.
     * */
    private fun initMapComponent() {
        TomTomMapManager.initTomTomMapComponent()
    }

}