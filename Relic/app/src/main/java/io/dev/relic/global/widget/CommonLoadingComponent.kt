package io.dev.relic.global.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import io.dev.relic.R
import io.dev.relic.ui.theme.mainThemeColor

@Composable
fun CommonLoadingComponent(
    modifier: Modifier = Modifier,
    backgroundColor: Color = mainThemeColor,
    iconSizeModifier: Modifier = Modifier
) {

    val commonLoadingRes: LottieComposition? by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.lottie_common_loading)
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = backgroundColor)
            .statusBarsPadding(),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = commonLoadingRes,
            modifier = iconSizeModifier.size(128.dp),
            restartOnPlay = true,
            iterations = Int.MAX_VALUE,
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun CommonLoadingComponentPreview() {
    CommonLoadingComponent()
}