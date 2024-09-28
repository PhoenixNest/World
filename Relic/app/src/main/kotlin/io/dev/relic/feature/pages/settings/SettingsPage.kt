package io.dev.relic.feature.pages.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import io.common.RelicConstants.URL.USER_PRIVACY
import io.common.RelicConstants.URL.USER_TERMS
import io.core.ui.CommonTopBar
import io.core.ui.theme.mainThemeColor
import io.dev.relic.R
import io.dev.relic.feature.activities.main.viewmodel.MainViewModel
import io.dev.relic.feature.activities.web.WebActivity
import io.dev.relic.feature.pages.settings.ui.SettingsPageContent
import io.dev.relic.feature.screens.main.MainScreenState

@Composable
fun SettingsPageRoute(
    mainScreenState: MainScreenState,
    mainViewModel: MainViewModel,
    onBackClick: () -> Unit
) {

    /* ======================== Common ======================== */

    val context = LocalContext.current

    SettingsPage(
        onBackClick = onBackClick,
        onToggleThemeClick = {},
        onUserAgreementClick = { WebActivity.redirect(context, USER_TERMS) },
        onPrivacyClick = { WebActivity.redirect(context, USER_PRIVACY) },
        onDebugClick = {}
    )
}

@Composable
fun SettingsPage(
    onBackClick: () -> Unit,
    onToggleThemeClick: () -> Unit,
    onUserAgreementClick: () -> Unit,
    onPrivacyClick: () -> Unit,
    onDebugClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = mainThemeColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            CommonTopBar(
                onBackClick = onBackClick,
                hasTitle = true,
                titleResId = R.string.settings_title
            )
            SettingsPageContent(
                onToggleThemeClick = onToggleThemeClick,
                onUserAgreementClick = onUserAgreementClick,
                onPrivacyClick = onPrivacyClick,
                onDebugClick = onDebugClick
            )
        }
    }
}

@Composable
@Preview
private fun SettingPagePreview() {
    SettingsPage(
        onBackClick = {},
        onToggleThemeClick = {},
        onUserAgreementClick = {},
        onPrivacyClick = {},
        onDebugClick = {}
    )
}