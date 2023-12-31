package io.module.map

import android.location.Location

interface ILocationListener {

    fun onAccessing()

    fun onAccessSucceed(location: Location)

    fun onAccessFailed(errorMessage: String)

}