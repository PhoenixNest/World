package io.dev.relic.global.widget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.dev.relic.R
import io.dev.relic.ui.theme.mainBackgroundColor

@Composable
fun CommonRetryComponent(
    onRetryClick: () -> Unit,
    containerHeight: Dp = 196.dp
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .height(containerHeight),
        shape = RoundedCornerShape(16.dp),
        backgroundColor = mainBackgroundColor
    ) {
        CommonHorizontalIconTextButton(
            iconResId = R.drawable.ic_retry,
            labelResId = R.string.no_data_retry,
            onClick = onRetryClick
        )
    }
}

@Composable
@Preview
private fun CommonRetryComponentPreview() {
    CommonRetryComponent(onRetryClick = {})
}