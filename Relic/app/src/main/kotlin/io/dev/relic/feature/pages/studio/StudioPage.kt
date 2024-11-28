package io.dev.relic.feature.pages.studio

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import io.core.ui.theme.RelicFontFamily

@Composable
fun StudioPageRoute() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Studio",
            fontSize = 32.sp,
            fontFamily = RelicFontFamily.googleSans
        )
    }
}