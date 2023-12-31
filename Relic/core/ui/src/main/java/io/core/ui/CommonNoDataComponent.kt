package io.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import io.core.ui.theme.RelicFontFamily.ubuntu
import io.core.ui.theme.mainTextColor
import io.core.ui.theme.mainThemeColor

@Composable
fun CommonNoDataComponent(
    modifier: Modifier = Modifier,
    backgroundColor: Color = mainThemeColor,
    iconSizeModifier: Modifier = Modifier,
    isShowText: Boolean = true
) {

    val commonLoadingRes: LottieComposition? by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.lottie_no_data)
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = backgroundColor)
            .statusBarsPadding(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.wrapContentSize()
        ) {
            LottieAnimation(
                composition = commonLoadingRes,
                modifier = iconSizeModifier.size(128.dp),
                restartOnPlay = true,
                iterations = Int.MAX_VALUE,
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop
            )
            if (isShowText) {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = stringResource(R.string.common_no_data),
                    style = TextStyle(
                        color = mainTextColor,
                        fontSize = 24.sp,
                        fontFamily = ubuntu,
                        textAlign = TextAlign.Center
                    )
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun CommonNoDataComponentPreview() {
    CommonNoDataComponent()
}