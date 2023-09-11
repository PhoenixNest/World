package io.dev.relic.feature.page.hive

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.dev.relic.feature.screen.main.MainState
import io.dev.relic.feature.activities.main.viewmodel.MainViewModel
import io.dev.relic.ui.theme.mainTextColor
import io.dev.relic.ui.theme.mainThemeColor

@Composable
fun HivePageRoute(mainViewModel: MainViewModel) {
    val mainState: MainState by mainViewModel.mainStateFlow.collectAsStateWithLifecycle()
    HivePage()
}

@Composable
private fun HivePage() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = mainThemeColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Hive",
            style = TextStyle(
                color = mainTextColor,
                fontSize = 30.sp
            )
        )
    }
}