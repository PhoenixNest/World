package io.dev.relic.domain.location

import android.location.Location

interface ILocationListener {

    fun onAccessing()

    fun onAccessSucceed(location: Location)

    fun onAccessFailed(errorMessage: String)

}