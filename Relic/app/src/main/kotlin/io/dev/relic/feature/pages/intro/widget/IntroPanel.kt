package io.dev.relic.feature.pages.intro.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.core.ui.CommonTextButton
import io.core.ui.dialog.CommonItemDivider
import io.core.ui.utils.RelicUiUtil.getCurrentScreenWidthDp
import io.dev.relic.R

@Composable
fun IntroPanel(
    isLargeMode: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp
                )
            )
            .navigationBarsPadding(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.Start
    ) {
        IntroTitlePanel()
        IntroFeaturePanel(
            isLargeMode = isLargeMode,
            onClick = onClick
        )
    }
}

@Composable
private fun IntroTitlePanel() {
    Spacer(modifier = Modifier.height(16.dp))
    Box(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .height(4.dp)
                .width(96.dp)
                .align(Alignment.Center)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                )
        )
    }
    Spacer(modifier = Modifier.height(32.dp))
    Row(
        modifier = Modifier.padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.intro_title),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.headlineLarge
        )
    }
    Spacer(modifier = Modifier.height(12.dp))
    Text(
        text = stringResource(R.string.intro_sub_title),
        modifier = Modifier.padding(horizontal = 24.dp),
        color = MaterialTheme.colorScheme.onPrimary,
        style = MaterialTheme.typography.headlineSmall
    )
    Spacer(modifier = Modifier.height(24.dp))
}

@Composable
private fun IntroFeaturePanel(
    isLargeMode: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp
                )
            )
            .padding(all = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        IntroFeatureItem(
            isLargeMode = isLargeMode,
            iconResId = R.drawable.ic_route,
            textResId = R.string.intro_feature_route
        )
        CommonItemDivider()
        IntroFeatureItem(
            isLargeMode = isLargeMode,
            iconResId = R.drawable.ic_coffee,
            textResId = R.string.intro_feature_coffee
        )
        CommonItemDivider()
        IntroFeatureItem(
            isLargeMode = isLargeMode,
            iconResId = R.drawable.ic_weather,
            textResId = R.string.intro_feature_weather
        )
        Spacer(modifier = Modifier.height(32.dp))
        CommonTextButton(
            textResId = R.string.intro_dive_in,
            onClick = onClick
        )
        Spacer(modifier = Modifier.height(24.dp))
        IntroPrivacy()
    }
}

@Composable
@Preview(showBackground = true)
private fun IntroPanelPreview() {
    IntroPanel(
        isLargeMode = false,
        onClick = {}
    )
}

@Composable
@Preview(
    showBackground = true,
    device = "spec:width=673dp,height=841dp,orientation=landscape"
)
private fun IntroPanelLargeModePreview() {
    val screenWidth = getCurrentScreenWidthDp()
    val panelWidth = screenWidth / 3
    IntroPanel(
        isLargeMode = true,
        onClick = {},
        modifier = Modifier.width(panelWidth)
    )
}

