package io.module.location

import android.location.Location

interface ILocationListener {

    fun onAccessing()

    fun onAccessSucceed(location: Location)

    fun onAccessFailed(errorMessage: String)

}