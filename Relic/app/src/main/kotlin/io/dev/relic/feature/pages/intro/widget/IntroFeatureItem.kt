package io.dev.relic.feature.pages.intro.widget

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.core.ui.CommonRoundIcon
import io.dev.relic.R

@Composable
fun IntroFeatureItem(
    isLargeMode: Boolean,
    @DrawableRes iconResId: Int,
    @StringRes textResId: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CommonRoundIcon(
            iconRes = iconResId,
            iconColor = MaterialTheme.colorScheme.onSurface,
            backgroundColor = MaterialTheme.colorScheme.surfaceContainerHighest
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = stringResource(textResId),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun IntroFeatureItemPreview() {
    IntroFeatureItem(
        isLargeMode = false,
        iconResId = R.drawable.ic_route,
        textResId = R.string.intro_feature_route
    )
}