package io.dev.relic.global.widget

import androidx.annotation.IntDef
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
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
import io.dev.relic.global.widget.LoadingType.Companion.CommonLoadingType
import io.dev.relic.global.widget.LoadingType.Companion.RecipesLoadingType
import io.dev.relic.global.widget.LoadingType.Companion.TodoLoadingType
import io.dev.relic.global.widget.LoadingType.Companion.WeatherLoadingType

@IntDef(
    CommonLoadingType,
    TodoLoadingType,
    WeatherLoadingType,
    RecipesLoadingType
)
annotation class LoadingType {
    companion object {
        const val CommonLoadingType = 0
        const val TodoLoadingType = 1
        const val WeatherLoadingType = 2
        const val RecipesLoadingType = 3
    }
}

@Composable
fun CommonLoadingComponent(
    modifier: Modifier = Modifier,
    @LoadingType type: Int = CommonLoadingType
) {

    val commonLoadingRes: LottieComposition? by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.lottie_common_loading)
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.DarkGray.copy(alpha = 0.1F))
            .statusBarsPadding(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Surface(
                modifier = modifier.size(128.dp),
                color = Color.LightGray,
                shape = CircleShape
            ) {
                LottieAnimation(
                    composition = commonLoadingRes,
                    modifier = modifier.fillMaxSize(),
                    restartOnPlay = true,
                    iterations = Int.MAX_VALUE,
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun CommonLoadingComponentPreview() {
    CommonLoadingComponent(type = CommonLoadingType)
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun TodoLoadingComponentPreview() {
    CommonLoadingComponent(type = TodoLoadingType)
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun WeatherLoadingComponentPreview() {
    CommonLoadingComponent(type = WeatherLoadingType)
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun RecipesLoadingComponentPreview() {
    CommonLoadingComponent(type = RecipesLoadingType)
}