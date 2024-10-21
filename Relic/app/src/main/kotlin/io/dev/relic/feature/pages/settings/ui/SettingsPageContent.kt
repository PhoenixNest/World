package io.dev.relic.feature.pages.settings.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.dev.relic.BuildConfig

@Composable
fun SettingsPageContent(
    onToggleThemeClick: () -> Unit,
    onUserAgreementClick: () -> Unit,
    onPrivacyClick: () -> Unit,
    onDebugClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        SettingsTermsPanel(
            onUserAgreementClick = onUserAgreementClick,
            onPrivacyClick = onPrivacyClick
        )
        if (BuildConfig.DEBUG) {
            SettingsDebugPanel()
        }
    }
}