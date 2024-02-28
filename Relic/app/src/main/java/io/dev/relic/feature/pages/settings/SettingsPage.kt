package io.dev.relic.feature.pages.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.core.ui.theme.mainThemeColor
import io.dev.relic.feature.activities.main.viewmodel.MainViewModel
import io.dev.relic.feature.pages.settings.ui.SettingsPageContent
import io.dev.relic.feature.screens.main.MainScreenState

@Composable
fun SettingsPageRoute(
    mainScreenState: MainScreenState,
    mainViewModel: MainViewModel,
    onBackClick: () -> Unit
) {
    SettingsPage(onBackClick = onBackClick)
}

@Composable
fun SettingsPage(onBackClick: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = mainThemeColor
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            SettingsPageContent()
        }
    }
}