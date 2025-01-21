package io.core.ui.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.core.ui.theme.RelicAppTheme

@Composable
fun CommonItemDivider(
    modifier: Modifier = Modifier,
    lineColor: Color = MaterialTheme.colorScheme.tertiary,
    thickness: Dp = 1.dp,
    horizontalMargin: Dp = 20.dp,
    verticalMargin: Dp = 16.dp
) {
    Surface(
        color = Color.Transparent,
        shape = RoundedCornerShape(12.dp)
    ) {
        HorizontalDivider(
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
}

@Composable
@Preview(showBackground = true)
private fun CommonItemDividerPreview() {
    RelicAppTheme {
        Box {
            CommonItemDivider()
        }
    }
}