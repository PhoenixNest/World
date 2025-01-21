package io.dev.relic.feature.pages.settings.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun SettingsPageContent(
    onToggleThemeClick: () -> Unit,
    onUserAgreementClick: () -> Unit,
    onPrivacyClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        item {
            SettingsTermsPanel(
                onUserAgreementClick = onUserAgreementClick,
                onPrivacyClick = onPrivacyClick
            )
        }
    }
}