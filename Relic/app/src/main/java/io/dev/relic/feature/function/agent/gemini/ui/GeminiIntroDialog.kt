package io.dev.relic.feature.function.agent.gemini.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import dev.jeziellago.compose.markdowntext.MarkdownText
import io.common.RelicConstants.ComposeUi.DEFAULT_DESC
import io.core.ui.theme.RelicFontFamily.googleSansDisplay
import io.core.ui.theme.mainTextColor
import io.core.ui.theme.mainThemeColor
import io.core.ui.theme.mainThemeColorAccent
import io.dev.relic.R
import io.dev.relic.feature.activities.web.WebActivity

private const val GEMINI_OFFICIAL_SITE = "https://deepmind.google/technologies/gemini/#introduction"

private val geminiBrush = Brush.linearGradient(
    listOf(
        Color(0xFF397AF1),
        Color(0xFFF7DCBC)
    )
)

@Composable
fun GeminiIntroDialog(
    onStartChatClick: () -> Unit,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = mainThemeColor.copy(alpha = 0.3F))
    ) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = true
            )
        ) {
            Surface(
                color = Color(0xFF060606),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(
                    width = 2.dp,
                    brush = geminiBrush
                ),
                elevation = 8.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    GeminiLottieComponent()
                    GeminiIntroDialogDesc(onStartChatClick = onStartChatClick)
                }
            }
        }
    }
}

@Composable
private fun GeminiIntroDialogDesc(onStartChatClick: () -> Unit) {
    val mainTitleTextStyle = TextStyle(
        brush = geminiBrush,
        fontSize = 16.sp,
        fontFamily = googleSansDisplay,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )

    val descTextStyle = TextStyle(
        color = mainTextColor
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        Color(0xFF060606),
                        Color.White.copy(alpha = 0.12F)
                    )
                )
            )
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            ),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.gemini_intro_title),
            style = mainTitleTextStyle
        )
        Spacer(modifier = Modifier.height(32.dp))
        MarkdownText(
            markdown = stringResource(R.string.gemini_intro_content),
            fontResource = io.core.ui.R.font.google_product_sans,
            style = descTextStyle
        )
        Spacer(modifier = Modifier.height(32.dp))
        GeminiIntroDialogStartChatButton(onClick = onStartChatClick)
        Spacer(modifier = Modifier.height(16.dp))
        GeminiIntroDialogSiteText()
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun GeminiIntroDialogStartChatButton(onClick: () -> Unit) {
    Surface(
        color = Color.Transparent,
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        listOf(
                            Color(0xFF0250C5),
                            Color(0xFFD43F8D)
                        )
                    )
                )
                .padding(vertical = 12.dp)
                .clickable { onClick.invoke() },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_agent_start_chat),
                contentDescription = DEFAULT_DESC,
                tint = mainTextColor
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "Chat with Gemini",
                style = TextStyle(
                    color = mainTextColor,
                    fontFamily = googleSansDisplay
                )
            )
        }
    }
}

@Composable
private fun GeminiIntroDialogSiteText() {
    val context = LocalContext.current
    val normalTextColor = mainTextColor
    val highLightTextColor = mainThemeColorAccent
    val annotatedText = buildAnnotatedString {
        append(stringResource(R.string.gemini_link_1))
        append(" ")
        pushStringAnnotation(
            tag = "URL",
            annotation = GEMINI_OFFICIAL_SITE
        )
        withStyle(
            style = SpanStyle(
                color = highLightTextColor,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append(stringResource(R.string.gemini_link_2))
        }
        pop()
    }

    ClickableText(
        text = annotatedText,
        modifier = Modifier.fillMaxWidth(),
        style = TextStyle(
            color = normalTextColor,
            fontSize = 11.sp,
            fontFamily = googleSansDisplay,
            textAlign = TextAlign.Center
        ),
        onClick = { offset ->
            annotatedText.getStringAnnotations(
                tag = "URL",
                start = offset,
                end = offset
            ).forEach { annotatedString ->
                WebActivity.redirect(
                    context = context,
                    url = annotatedString.item
                )
            }
        }
    )
}

@Composable
@Preview
private fun GeminiIntroDialogPreview() {
    GeminiIntroDialog(
        onStartChatClick = {},
        onDismiss = {}
    )
}