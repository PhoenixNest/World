package io.dev.relic.core.sdk.ad.model

import java.util.Calendar

data class AdInfoWrapper(

    /**
     * Instance of ad object.
     * */
    val adObject: Any,

    /**
     * The beginning timestamp of ad object start to load.
     * */
    val timeStamp: Long = Calendar.getInstance().timeInMillis

)