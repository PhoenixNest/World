package io.dev.relic.feature.pages.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.common.RelicConstants.URL.USER_PRIVACY
import io.common.RelicConstants.URL.USER_TERMS
import io.dev.relic.feature.activities.web.WebActivity
import io.dev.relic.feature.pages.settings.ui.SettingsPageContent

@Composable
fun SettingsPageRoute(onBackClick: () -> Unit) {

    /* ======================== Common ======================== */

    val context = LocalContext.current

    SettingsPage(
        onBackClick = onBackClick,
        onToggleThemeClick = {},
        onUserAgreementClick = { WebActivity.redirect(context, USER_TERMS) },
        onPrivacyClick = { WebActivity.redirect(context, USER_PRIVACY) },
    )
}

@Composable
fun SettingsPage(
    onBackClick: () -> Unit,
    onToggleThemeClick: () -> Unit,
    onUserAgreementClick: () -> Unit,
    onPrivacyClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 32.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {

            SettingsPageContent(
                onToggleThemeClick = onToggleThemeClick,
                onUserAgreementClick = onUserAgreementClick,
                onPrivacyClick = onPrivacyClick
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun SettingPagePreview() {
    SettingsPage(
        onBackClick = {},
        onToggleThemeClick = {},
        onUserAgreementClick = {},
        onPrivacyClick = {}
    )
}