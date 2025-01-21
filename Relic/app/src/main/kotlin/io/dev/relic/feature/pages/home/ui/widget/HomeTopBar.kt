package io.dev.relic.feature.pages.home.ui.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.common.RelicConstants.ComposeUi.DEFAULT_DESC
import io.core.ui.theme.RelicAppTheme
import io.core.ui.theme.RelicFontFamily.googleSans
import io.dev.relic.R

@Stable
data class HomeTopBarAction(
    val onOpenDrawerClick: () -> Unit,
    val onSettingClick: () -> Unit
)

@Composable
fun HomeTopBar(
    isCompactMode: Boolean,
    action: HomeTopBarAction,
    modifier: Modifier = Modifier
) {

    val titleRes = if (isCompactMode) {
        R.string.home_title
    } else {
        R.string.home_title_expend_mode
    }

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surfaceContainer
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
        ) {
            IconButton(
                onClick = action.onOpenDrawerClick,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_apps),
                    contentDescription = DEFAULT_DESC,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
            Text(
                text = stringResource(titleRes),
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.onSurface,
                fontFamily = googleSans,
                style = MaterialTheme.typography.titleMedium
            )
            IconButton(
                onClick = action.onSettingClick,
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_settings),
                    contentDescription = DEFAULT_DESC,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun HomeTopBarPreview() {
    RelicAppTheme {
        HomeTopBar(
            isCompactMode = true,
            action = HomeTopBarAction(
                onOpenDrawerClick = {},
                onSettingClick = {}
            )
        )
    }
}