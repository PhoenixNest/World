package io.dev.relic.feature.pages.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.common.RelicConstants.ComposeUi.DEFAULT_DESC
import io.common.RelicConstants.URL.USER_PRIVACY
import io.common.RelicConstants.URL.USER_TERMS
import io.core.ui.theme.RelicAppTheme
import io.core.ui.theme.RelicFontFamily.googleSans
import io.dev.relic.R
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
                .statusBarsPadding(),
            verticalArrangement = Arrangement.spacedBy(
                space = 12.dp,
                alignment = Alignment.Top
            ),
            horizontalAlignment = Alignment.Start
        ) {
            SettingPageTopBar(onBackClick = onBackClick)
            SettingsPageContent(
                onToggleThemeClick = onToggleThemeClick,
                onUserAgreementClick = onUserAgreementClick,
                onPrivacyClick = onPrivacyClick
            )
        }
    }
}

@Composable
private fun SettingPageTopBar(onBackClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterStart
    ) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                painter = painterResource(io.core.ui.R.drawable.ic_back),
                contentDescription = DEFAULT_DESC,
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
        Text(
            text = stringResource(R.string.settings_title),
            modifier = Modifier.align(Alignment.Center),
            color = MaterialTheme.colorScheme.onSurface,
            fontFamily = googleSans,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun SettingPagePreview() {
    RelicAppTheme {
        SettingsPage(
            onBackClick = {},
            onToggleThemeClick = {},
            onUserAgreementClick = {},
            onPrivacyClick = {}
        )
    }
}