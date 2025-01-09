package io.dev.relic.feature.pages.home.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.core.ui.theme.RelicFontFamily.googleSans

@Composable
fun HomeTitleBar(titleStr: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.Start
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(
            modifier = Modifier
                .height(14.dp)
                .width(4.dp)
                .background(
                    color = MaterialTheme.colorScheme.inverseSurface,
                    shape = RoundedCornerShape(12.dp)
                )
        )
        Text(
            text = titleStr,
            color = MaterialTheme.colorScheme.onSurface,
            fontFamily = googleSans,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun HomeTitleBarPreview() {
    HomeTitleBar("HOME")
}