package io.dev.relic.feature.pages.studio.ui.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.common.RelicConstants
import io.core.ui.theme.RelicFontFamily
import io.core.ui.theme.mainButtonColorLight
import io.core.ui.theme.mainTextColor
import io.dev.relic.R

@Composable
fun StudioTabBar(
    onUserClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .systemBarsPadding()
    ) {
        IconButton(
            onClick = onUserClick,
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_user),
                contentDescription = RelicConstants.ComposeUi.DEFAULT_DESC,
                tint = mainButtonColorLight
            )
        }
        Text(
            text = stringResource(id = R.string.studio_label),
            modifier = Modifier.align(Alignment.Center),
            style = TextStyle(
                color = mainTextColor,
                fontFamily = RelicFontFamily.ubuntu,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
@Preview
private fun StudioTabBarPreview() {
    StudioTabBar(onUserClick = {})
}