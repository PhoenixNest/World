package io.dev.relic.feature.screens.intro

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
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
import io.dev.relic.R
import io.dev.relic.feature.activities.web.WebActivity
import io.dev.relic.global.RelicConstants
import io.dev.relic.global.widget.CommonRoundIcon
import io.dev.relic.global.widget.CommonTextButton
import io.dev.relic.global.widget.dialog.CommonItemDivider
import io.dev.relic.ui.theme.RelicFontFamily
import io.dev.relic.ui.theme.mainTextColor
import io.dev.relic.ui.theme.mainTextColorDark
import io.dev.relic.ui.theme.mainThemeColorAccent

@Composable
fun IntroScreen(onClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.mipmap.intro),
            contentDescription = RelicConstants.ComposeUi.DEFAULT_DESC,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        IntroPanel(onClick = onClick)
    }
}

@Composable
private fun BoxScope.IntroPanel(onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
            .background(
                color = mainThemeColorAccent.copy(alpha = 0.9F),
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp
                )
            )
            .navigationBarsPadding(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.Start
    ) {
        IntroTitlePanel()
        IntroFeaturePanel(onClick = onClick)
    }
}

@Composable
private fun IntroTitlePanel() {
    Spacer(modifier = Modifier.height(16.dp))
    Box(modifier = Modifier.fillMaxWidth()) {
        Box(
            Modifier
                .height(4.dp)
                .width(96.dp)
                .align(Alignment.Center)
                .background(color = mainTextColor, shape = RoundedCornerShape(12.dp))
        )
    }
    Spacer(modifier = Modifier.height(32.dp))
    Row(
        modifier = Modifier.padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.intro_title),
            style = TextStyle(
                color = mainTextColor,
                fontSize = 52.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = RelicFontFamily.ubuntu
            )
        )
    }
    Spacer(modifier = Modifier.height(12.dp))
    Text(
        text = stringResource(R.string.intro_sub_title),
        modifier = Modifier.padding(horizontal = 24.dp),
        style = TextStyle(
            color = mainTextColor,
            fontSize = 20.sp,
            fontFamily = RelicFontFamily.ubuntu
        )
    )
    Spacer(modifier = Modifier.height(24.dp))
}

@Composable
private fun IntroFeaturePanel(onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.White.copy(alpha = 0.36F),
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp
                )
            )
            .padding(all = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        IntroFeatureItem(
            iconResId = R.drawable.ic_route,
            textResId = R.string.intro_feature_route
        )
        CommonItemDivider()
        IntroFeatureItem(
            iconResId = R.drawable.ic_coffee,
            textResId = R.string.intro_feature_coffee
        )
        CommonItemDivider()
        IntroFeatureItem(
            iconResId = R.drawable.ic_weather,
            textResId = R.string.intro_feature_weather
        )
        Spacer(modifier = Modifier.height(32.dp))
        CommonTextButton(
            textResId = R.string.intro_dive_in,
            onClick = onClick
        )
        Spacer(modifier = Modifier.height(24.dp))
        IntroPrivacy()
    }
}

@Composable
private fun IntroPrivacy() {
    val context: Context = LocalContext.current
    val normalTextColor: Color = mainTextColorDark
    val highLightTextColor: Color = mainTextColorDark
    val annotatedText: AnnotatedString = buildAnnotatedString {
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
        onClick = { offset: Int ->
            annotatedText.getStringAnnotations(
                tag = "URL",
                start = offset,
                end = offset
            ).forEach { annotatedString: AnnotatedString.Range<String> ->
                WebActivity.redirect(
                    context = context,
                    httpUrl = annotatedString.item
                )
            }
        }
    )
}

@Composable
private fun IntroFeatureItem(
    @DrawableRes iconResId: Int,
    @StringRes textResId: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CommonRoundIcon(iconResId)
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            stringResource(textResId),
            style = TextStyle(
                color = mainTextColorDark,
                fontSize = 16.sp,
                fontFamily = RelicFontFamily.ubuntu
            )
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun IntroScreenPreview() {
    IntroScreen(onClick = {})
}