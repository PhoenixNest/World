package io.dev.relic.feature.screens.login

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LoginScreen(onLoginWithGoogle: () -> Unit) {
    //
}

@Composable
@Preview(showBackground = true)
private fun LoginScreenPreview() {
    LoginScreen(onLoginWithGoogle = {})
}