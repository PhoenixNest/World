package io.dev.relic.feature.pages.intro.widget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import io.common.RelicConstants
import io.core.ui.theme.RelicFontFamily
import io.core.ui.theme.mainTextColor
import io.core.ui.theme.mainTextColorDark
import io.dev.relic.R
import io.dev.relic.feature.activities.web.WebActivity

@Composable
fun IntroPrivacy() {
    val context = LocalContext.current
    val normalTextColor = mainTextColorDark
    val highLightTextColor = mainTextColor
    val annotatedText = buildAnnotatedString {
        append(stringResource(R.string.intro_agreement_part_1))
        pushStringAnnotation(
            tag = "URL",
            annotation = RelicConstants.URL.USER_TERMS
        )
        withStyle(
            style = SpanStyle(
                color = highLightTextColor,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append(stringResource(R.string.intro_agreement_part_2))
        }
        pop()
        append(stringResource(R.string.intro_agreement_part_3))
        pushStringAnnotation(
            tag = "URL",
            annotation = RelicConstants.URL.USER_PRIVACY
        )
        withStyle(
            style = SpanStyle(
                color = highLightTextColor,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append(stringResource(R.string.intro_agreement_part_4))
        }
        pop()
    }

    ClickableText(
        text = annotatedText,
        modifier = Modifier.fillMaxWidth(),
        style = TextStyle(
            color = normalTextColor,
            fontSize = 11.sp,
            fontFamily = RelicFontFamily.ubuntu,
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