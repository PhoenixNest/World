package io.dev.relic.feature.splash

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import io.dev.relic.R
import io.dev.relic.feature.main.MainActivity

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun SplashScreen(modifier: Modifier = Modifier) {

    val context: Context = LocalContext.current

    val composition: LottieComposition? by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.lottie_common_loading)
    )

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            composition = composition,
            modifier = modifier.size(120.dp),
            restartOnPlay = true,
            iterations = Int.MAX_VALUE
        )
        Spacer(modifier = modifier.height(8.dp))
        TextButton(
            onClick = {
                MainActivity.start(context)
            }
        ) {
            Text(text = "Splash")
        }
    }
}