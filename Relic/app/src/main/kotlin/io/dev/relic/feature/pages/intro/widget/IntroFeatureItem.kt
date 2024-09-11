package io.dev.relic.feature.pages.intro.widget

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.core.ui.CommonRoundIcon
import io.core.ui.theme.RelicFontFamily
import io.core.ui.theme.mainTextColor

@Composable
fun IntroFeatureItem(
    isLargeMode: Boolean,
    @DrawableRes iconResId: Int,
    @StringRes textResId: Int
) {
    val textSize = if (isLargeMode) 14.sp else 16.sp
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CommonRoundIcon(iconResId)
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            stringResource(textResId),
            style = TextStyle(
                color = mainTextColor,
                fontSize = textSize,
                fontFamily = RelicFontFamily.ubuntu
            )
        )
    }
}