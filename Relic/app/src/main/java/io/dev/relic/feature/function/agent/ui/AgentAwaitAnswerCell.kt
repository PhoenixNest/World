package io.dev.relic.feature.function.agent.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import io.dev.relic.R

@Composable
fun AgentAwaitAnswerCell() {
    val lottieRes by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.lottie_awaiting_answer)
    )
    
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = lottieRes,
            restartOnPlay = true,
            iterations = Int.MAX_VALUE,
            alignment = Alignment.Center
        )
    }
}

@Composable
@Preview
private fun AgentAwaitAnswerCellPreview() {
    AgentAwaitAnswerCell()
}