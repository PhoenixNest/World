package io.dev.relic.feature.pages.settings.ui.widget

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.core.ui.theme.RelicFontFamily.ubuntu
import io.core.ui.theme.mainTextColor50
import io.dev.relic.R

@Composable
fun SettingsPanelTitle(@StringRes titleResId: Int) {
    Text(
        text = stringResource(id = titleResId),
        modifier = Modifier.padding(horizontal = 16.dp),
        style = TextStyle(
            color = mainTextColor50,
            fontSize = 16.sp,
            fontFamily = ubuntu,
            fontWeight = FontWeight.Bold
        )
    )
}

@Composable
@Preview
private fun SettingsPanelTitlePreview() {
    SettingsPanelTitle(R.string.settings_title_debug)
}