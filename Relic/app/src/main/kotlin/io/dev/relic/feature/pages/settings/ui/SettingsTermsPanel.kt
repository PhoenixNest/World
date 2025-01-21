package io.dev.relic.feature.pages.settings.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.common.RelicConstants.ComposeUi.DEFAULT_DESC
import io.core.ui.theme.RelicAppTheme
import io.dev.relic.R

@Composable
fun SettingsTermsPanel(
    onUserAgreementClick: () -> Unit,
    onPrivacyClick: () -> Unit
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
private fun SettingsPanelTitle(@StringRes titleResId: Int) {
    Text(
        text = stringResource(id = titleResId),
        modifier = Modifier.padding(horizontal = 16.dp),
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5F),
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.titleMedium
    )
}

@Composable
private fun SettingsPanelItem(
    @StringRes labelResId: Int,
    @DrawableRes iconResId: Int,
    onItemClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick.invoke() }
            .padding(16.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = DEFAULT_DESC,
            tint = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = stringResource(id = labelResId),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun SettingsTermsPanelPreview() {
    RelicAppTheme {
        SettingsTermsPanel(
            onUserAgreementClick = {},
            onPrivacyClick = {}
        )
    }
}