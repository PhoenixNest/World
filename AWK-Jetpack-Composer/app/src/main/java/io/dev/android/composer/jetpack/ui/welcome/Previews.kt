package io.dev.android.composer.jetpack.ui.welcome

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.dev.android.composer.jetpack.ui.theme.AWKJetpackComposerTheme

@Preview(showBackground = true)
@Composable
fun WelcomePagePreview() {
    AWKJetpackComposerTheme {
        WelcomePage()
    }
}