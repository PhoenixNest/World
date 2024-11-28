package io.dev.relic.feature.screens.main

import android.location.Location

sealed class LocationState {

    /* Common */

    data object Init : LocationState()

    data object Empty : LocationState()

    /* Loading */

    data object AccessingLocation : LocationState()

    /* Succeed */

    data class AccessLocationSucceed(
        val location: Location?
    ) : LocationState()

    /* Failed */

    data class AccessLocationFailed(
        val errorCode: Int?,
        val errorMessage: String?
    ) : LocationState()

}