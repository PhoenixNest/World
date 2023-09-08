package io.dev.relic.feature.page.home

import android.location.Location
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.dev.relic.feature.activities.main.viewmodel.MainViewModel
import io.dev.relic.feature.screen.main.MainState
import io.dev.relic.global.widget.CommonLoadingComponent
import io.dev.relic.global.widget.CommonNoDataComponent
import io.dev.relic.ui.theme.mainTextColor
import io.dev.relic.ui.theme.mainThemeColor

@Composable
fun HomePageRoute(mainViewModel: MainViewModel) {
    when (val mainState: MainState = mainViewModel.mainStateFlow.collectAsStateWithLifecycle().value) {
        MainState.Init -> {}
        MainState.Empty -> CommonNoDataComponent()
        MainState.AccessingLocation -> CommonLoadingComponent()
        is MainState.AccessLocationFailed -> CommonNoDataComponent()
        is MainState.AccessLocationSucceed -> HomePage(mainState.location)
    }
}

@Composable
private fun HomePage(location: Location?) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = mainThemeColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Home",
            style = TextStyle(
                color = mainTextColor,
                fontSize = 30.sp
            )
        )
    }
}