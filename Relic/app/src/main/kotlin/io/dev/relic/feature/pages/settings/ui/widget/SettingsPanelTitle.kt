package io.dev.relic.feature.pages.settings.ui.widget

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.dev.relic.R

@Composable
fun SettingsPanelTitle(@StringRes titleResId: Int) {
    Text(
        text = stringResource(id = titleResId),
        modifier = Modifier.padding(horizontal = 16.dp),
        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5F),
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.titleMedium
    )
}

@Composable
@Preview(showBackground = true)
private fun SettingsPanelTitlePreview() {
    SettingsPanelTitle(R.string.settings_title_debug)
}