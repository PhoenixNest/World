package io.core.ui.dialog

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CommonItemDivider(
    modifier: Modifier = Modifier,
    lineColor: Color = MaterialTheme.colorScheme.tertiary,
    thickness: Dp = 1.dp,
    horizontalMargin: Dp = 20.dp,
    verticalMargin: Dp = 16.dp
) {
    Divider(
        modifier = modifier
            .padding(
                horizontal = horizontalMargin,
                vertical = verticalMargin
            )
            .fillMaxWidth(),
        color = lineColor,
        thickness = thickness
    )
}