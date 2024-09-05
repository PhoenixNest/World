package io.core.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.common.RelicConstants
import io.core.ui.theme.mainThemeColor

@Composable
fun CommonRoundIcon(
    @DrawableRes iconRes: Int,
    containerSize: Dp = 64.dp,
    iconSize: Dp = 32.dp,
    iconColor: Color = Color.White,
    backgroundColor: Color = mainThemeColor
) {
    Box(
        modifier = Modifier
            .size(containerSize)
            .background(
                color = backgroundColor,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = RelicConstants.ComposeUi.DEFAULT_DESC,
            modifier = Modifier.size(iconSize),
            tint = iconColor
        )
    }
}

@Composable
@Preview
private fun CommonRoundIconPreview() {
    CommonRoundIcon(R.drawable.ic_retry)
}