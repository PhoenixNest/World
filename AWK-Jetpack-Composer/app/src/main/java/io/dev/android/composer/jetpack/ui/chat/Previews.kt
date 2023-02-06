package io.dev.android.composer.jetpack.ui.chat

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.dev.android.composer.jetpack.R
import io.dev.android.composer.jetpack.model.chat.Message
import io.dev.android.composer.jetpack.model.chat.User
import io.dev.android.composer.jetpack.ui.theme.AWKJetpackComposerTheme

@Preview(
    name = "Light Mode",
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun MessageCardPreview() {
    val user = User(0, "Android", R.drawable.ic_android)
    val message = Message(0, user, "Hello from Google")
    MessageCard(msg = message)
}

@Preview(showBackground = true)
@Composable
fun MessageListPreview() {
    AWKJetpackComposerTheme {
        MessageList(messages = Message.testConversation())
    }
}