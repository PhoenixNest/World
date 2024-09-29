package io.core.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CommonRetryComponent(
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerHeight: Dp = 196.dp,
    backgroundColor: Color = MaterialTheme.colorScheme.primaryContainer
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(containerHeight),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CommonHorizontalIconTextButton(
                iconResId = R.drawable.ic_retry,
                labelResId = R.string.common_retry,
                onClick = onRetryClick
            )
        }
    }
}

@Composable
@Preview
private fun CommonRetryComponentPreview() {
    CommonRetryComponent(onRetryClick = {})
}