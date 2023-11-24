package io.ui.dialog

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.ui.theme.dividerColor

@Composable
fun CommonItemDivider(
    modifier: Modifier = Modifier,
    lineColor: Color = dividerColor,
    thickness: Dp = 1.dp
) {
    Divider(
        modifier = modifier
            .padding(
                horizontal = 20.dp,
                vertical = 16.dp
            )
            .fillMaxWidth(),
        color = lineColor,
        thickness = thickness
    )
}