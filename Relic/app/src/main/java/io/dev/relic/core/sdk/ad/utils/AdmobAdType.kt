package io.dev.relic.core.sdk.ad.utils

import androidx.annotation.StringDef

@StringDef
@Retention(AnnotationRetention.SOURCE)
annotation class AdmobAdType {
    companion object {
        const val OPEN_AD = "ad_type_open_ad"
        const val REWARD_AD = "ad_type_reward_ad"
        const val BANNER_AD = "ad_type_banner_ad"
        const val NATIVE_AD = "ad_type_native_ad"
        const val INTERSTITIAL_AD = "ad_type_interstitial_ad"
    }
}