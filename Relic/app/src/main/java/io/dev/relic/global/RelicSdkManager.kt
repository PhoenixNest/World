package io.dev.relic.global

import android.content.Context
import io.agent.gemini.GeminiAgent
import io.agent.gemini.R
import io.common.RelicResCenter.getString
import io.module.ad.admob.AdmobAdManager

object RelicSdkManager {

    /**
     * Initialize global sdk.
     * */
    fun initSdk(context: Context) {
        initAdmob(context)
        initGemini()
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
     * Initialize the gemini sdk.
     * */
    private fun initGemini() {
        val devKey = getString(R.string.agent_gemini_dev_key)
        GeminiAgent.initGeminiComponent(devKey)
    }

}