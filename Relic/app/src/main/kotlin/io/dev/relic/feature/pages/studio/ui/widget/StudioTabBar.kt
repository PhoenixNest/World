package io.dev.relic.feature.pages.studio.ui.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.common.RelicConstants
import io.dev.relic.R

@Composable
fun StudioTabBar(
    onUserClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = 8.dp,
                end = 8.dp,
                top = 32.dp
            )
    ) {
        IconButton(
            onClick = onUserClick,
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                painter = painterResource(id = io.core.ui.R.drawable.ic_user),
                contentDescription = RelicConstants.ComposeUi.DEFAULT_DESC,
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        Text(
            text = stringResource(id = R.string.studio_label),
            modifier = Modifier.align(Alignment.Center),
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun StudioTabBarPreview() {
    StudioTabBar(onUserClick = {})
}