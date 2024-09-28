package io.dev.relic.feature.pages.detail.news

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.common.RelicConstants.Common.ANDROID
import io.common.RelicConstants.URL.DEFAULT_PLACEHOLDER_URL
import io.core.ui.CommonComposeWebView
import io.core.ui.CommonTopBar
import io.core.ui.theme.mainIconColorLight
import io.core.ui.theme.mainThemeColor

@Composable
fun NewsDetailPageRoute(
    title: String,
    contentUrl: String,
    onBackClick: () -> Unit
) {
    NewsDetailPage(
        title = title,
        contentUrl = contentUrl,
        onBackClick = onBackClick
    )
}

@Composable
private fun NewsDetailPage(
    title: String,
    contentUrl: String,
    onBackClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = mainThemeColor
    ) {
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .fillMaxSize()
        ) {
            CommonTopBar(
                onBackClick = onBackClick,
                hasTitle = true,
                title = title,
                iconColor = mainIconColorLight
            )
            CommonComposeWebView(redirectUrl = contentUrl)
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun NewsDetailPagePreview() {
    NewsDetailPage(
        title = ANDROID,
        contentUrl = DEFAULT_PLACEHOLDER_URL,
        onBackClick = {}
    )
}