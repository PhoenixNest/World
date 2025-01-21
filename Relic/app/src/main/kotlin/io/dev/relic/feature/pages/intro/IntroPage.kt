package io.dev.relic.feature.pages.intro

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.core.ui.theme.RelicAppTheme
import io.dev.relic.feature.pages.intro.widget.IntroPanel

@Composable
fun IntroPage(
    isCompatMode: Boolean,
    onNavigateClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primary)
    ) {
        IntroPanel(
            isLargeMode = false,
            onClick = onNavigateClick,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun IntroPagePreview() {
    RelicAppTheme {
        IntroPage(
            isCompatMode = false,
            onNavigateClick = {}
        )
    }
}

@Composable
@Preview(showBackground = true, device = "id:pixel_fold")
private fun IntroPageExpendModePreview() {
    RelicAppTheme {
        IntroPage(
            isCompatMode = true,
            onNavigateClick = {}
        )
    }
}