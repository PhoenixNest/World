package io.dev.relic.feature.pages.settings.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.dev.relic.R
import io.dev.relic.feature.pages.settings.ui.widget.SettingsPanelItem
import io.dev.relic.feature.pages.settings.ui.widget.SettingsPanelTitle

@Composable
fun SettingsTermsPanel(
    onUserAgreementClick: () -> Unit,
    onPrivacyClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        SettingsPanelTitle(R.string.settings_title_terms)
        Spacer(modifier = Modifier.height(16.dp))
        SettingsPanelItem(
            labelResId = R.string.settings_terms_user_agreement,
            iconResId = io.core.ui.R.drawable.ic_user_agreement,
            onItemClick = onUserAgreementClick
        )
        SettingsPanelItem(
            labelResId = R.string.settings_terms_privacy_agreement,
            iconResId = R.drawable.ic_privacy_agreement,
            onItemClick = onPrivacyClick
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF282C34)
private fun SettingsTermsPanelPreview() {
    SettingsTermsPanel(
        onUserAgreementClick = {},
        onPrivacyClick = {}
    )
}