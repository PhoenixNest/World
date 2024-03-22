package io.dev.relic.feature.pages.studio.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import io.core.ui.theme.RelicFontFamily.ubuntu
import io.core.ui.theme.mainTextColor
import io.dev.relic.R

@Composable
fun StudioPageContent() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = R.string.studio_label),
                style = TextStyle(
                    color = mainTextColor,
                    fontSize = 32.sp,
                    fontFamily = ubuntu
                )
            )
        }
    }
}

@Composable
@Preview
private fun StudioPageContentPreview() {
    StudioPageContent()
}