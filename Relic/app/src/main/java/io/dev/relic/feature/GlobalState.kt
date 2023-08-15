package io.dev.relic.feature

import android.location.Location

sealed class GlobalState {

    /* Common */

    object Init : GlobalState()

    object Empty : GlobalState()

    /* Loading */

    object AccessingLocation : GlobalState()

    /* Succeed */

    data class AccessLocationSucceed(
        val location: Location?
    ) : GlobalState()

    /* Failed */

    data class AccessLocationFailed(
        val errorCode: Int?,
        val errorMessage: String?
    ) : GlobalState()

}