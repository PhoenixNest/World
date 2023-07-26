package io.dev.relic.feature.common.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import io.dev.relic.R
import io.dev.relic.global.widget.CommonTopBar

@Composable
fun SettingScreenRoute(onBackClick: () -> Unit) {
    SettingScreen(onBackClick = onBackClick)
}

@Composable
private fun SettingScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
        color = Color.LightGray.copy(alpha = 0.1F)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(
                    state = rememberScrollState(),
                    enabled = true
                ),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            CommonTopBar(
                onBackClick = onBackClick,
                titleResId = R.string.setting_title
            )
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun SettingScreenPreview() {
    SettingScreen(
        onBackClick = {}
    )
}