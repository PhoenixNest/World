package io.dev.relic.feature.page.explore

import android.content.Context
import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.dev.relic.feature.activities.main.viewmodel.MainViewModel
import io.dev.relic.domain.location.amap.CommonAMapComponent
import io.dev.relic.feature.screen.main.MainScreenState
import io.dev.relic.feature.screen.main.MainState
import io.dev.relic.ui.theme.mainThemeColor

@Composable
fun ExplorePageRoute(
    mainScreenState: MainScreenState,
    mainViewModel: MainViewModel
) {
    val context: Context = LocalContext.current
    val lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle
    val mainState: MainState by mainViewModel.mainStateFlow.collectAsStateWithLifecycle()
    val mainSavedInstanceState: Bundle? = mainScreenState.saveInstanceState

    ExplorePage(
        context = context,
        lifecycle = lifecycle,
        mainState = mainState,
        mainSavedInstanceState = mainSavedInstanceState
    )
}

@Composable
private fun ExplorePage(
    context: Context,
    lifecycle: Lifecycle,
    mainState: MainState,
    mainSavedInstanceState: Bundle?
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = mainThemeColor)
    ) {
        CommonAMapComponent(
            context = context,
            lifecycle = lifecycle,
            mainSavedInstanceState = mainSavedInstanceState
        )
    }
}