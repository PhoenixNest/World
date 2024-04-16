package io.dev.relic.feature.function.agent.gemini.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import io.common.RelicConstants.ComposeUi.DEFAULT_DESC
import io.core.ui.theme.RelicFontFamily.googleSansDisplay
import io.core.ui.theme.mainTextColorDark
import io.core.ui.theme.mainThemeColorLight
import io.dev.relic.R

@Composable
fun GeminiLottieComponent() {

    val lottieRes by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.lottie_gemini)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
    ) {
        Image(
            painter = painterResource(id = R.mipmap.gemini_mind),
            contentDescription = DEFAULT_DESC,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        LottieAnimation(
            composition = lottieRes,
            modifier = Modifier.fillMaxSize(),
            restartOnPlay = false,
            speed = 0.6F,
            iterations = Int.MAX_VALUE,
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop
        )
        Text(
            text = stringResource(R.string.gemini),
            modifier = Modifier
                .align(Alignment.Center)
                .wrapContentSize()
                .background(
                    color = mainThemeColorLight,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(
                    horizontal = 16.dp,
                    vertical = 6.dp
                ),
            style = TextStyle(
                color = mainTextColorDark,
                fontFamily = googleSansDisplay,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        )
    }
}

@Composable
@Preview
private fun GeminiLottieComponentPreview() {
    GeminiLottieComponent()
}