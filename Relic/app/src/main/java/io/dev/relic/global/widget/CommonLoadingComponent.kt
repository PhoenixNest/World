package io.dev.relic.global.widget

import androidx.annotation.IntDef
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import io.dev.relic.R
import io.dev.relic.global.widget.LoadingType.Companion.COMMON
import io.dev.relic.global.widget.LoadingType.Companion.RECIPES
import io.dev.relic.global.widget.LoadingType.Companion.TODO
import io.dev.relic.global.widget.LoadingType.Companion.WEATHER
import io.dev.relic.ui.theme.mainThemeColor

@IntDef(
    COMMON,
    TODO,
    WEATHER,
    RECIPES
)
annotation class LoadingType {
    companion object {
        const val COMMON = 0
        const val TODO = 1
        const val WEATHER = 2
        const val RECIPES = 3
    }
}

@Composable
fun CommonLoadingComponent(
    modifier: Modifier = Modifier,
    @LoadingType type: Int = COMMON
) {

    val commonLoadingRes: LottieComposition? by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.lottie_common_loading)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = mainThemeColor)
            .statusBarsPadding(),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = commonLoadingRes,
            modifier = modifier.size(128.dp),
            restartOnPlay = true,
            iterations = Int.MAX_VALUE,
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun CommonLoadingComponentPreview() {
    CommonLoadingComponent(type = COMMON)
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun TodoLoadingComponentPreview() {
    CommonLoadingComponent(type = TODO)
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun WeatherLoadingComponentPreview() {
    CommonLoadingComponent(type = WEATHER)
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun RecipesLoadingComponentPreview() {
    CommonLoadingComponent(type = RECIPES)
}