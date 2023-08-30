package io.dev.relic.global.widget.dialog

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.dev.relic.ui.theme.dividerColor

@Composable
fun CommonItemDivider(modifier: Modifier = Modifier) {
    Divider(
        modifier = modifier
            .padding(
                horizontal = 20.dp,
                vertical = 16.dp
            )
            .fillMaxWidth(),
        color = dividerColor
    )
}