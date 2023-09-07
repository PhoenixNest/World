package io.dev.relic.feature.screen.main

import android.location.Location

sealed class MainState {

    /* Common */

    object Init : MainState()

    object Empty : MainState()

    /* Loading */

    object AccessingLocation : MainState()

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