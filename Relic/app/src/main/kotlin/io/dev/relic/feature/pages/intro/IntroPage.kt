package io.dev.relic.feature.pages.intro

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import io.common.RelicConstants
import io.dev.relic.R
import io.dev.relic.feature.pages.intro.widget.IntroPanel

@Composable
fun IntroPage(onNavigateClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.mipmap.intro),
            contentDescription = RelicConstants.ComposeUi.DEFAULT_DESC,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
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
@Preview
private fun IntroPagePreview() {
    IntroPage(onNavigateClick = {})
}