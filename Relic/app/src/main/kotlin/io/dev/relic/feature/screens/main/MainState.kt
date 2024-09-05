package io.dev.relic.feature.screens.main

import android.location.Location

sealed class MainState {

    /* Common */

    data object Init : MainState()

    data object Empty : MainState()

    /* Loading */

    data object AccessingLocation : MainState()

    /* Succeed */

    data class AccessLocationSucceed(
        val location: Location?
    ) : MainState()

    /* Failed */

    data class AccessLocationFailed(
        val errorCode: Int?,
        val errorMessage: String?
    ) : MainState()

}