package io.dev.relic.feature.pages.explore.widget

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.dev.relic.R
import io.common.RelicConstants
import io.core.ui.theme.RelicFontFamily
import io.core.ui.theme.mainTextColor
import io.core.ui.theme.mainThemeColorAccent

@Composable
fun ExploreExtendFloatingActionButton(
    @StringRes labelRedId: Int,
    @DrawableRes iconResId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ExtendedFloatingActionButton(
        text = {
            Text(
                text = stringResource(id = labelRedId),
                style = TextStyle(
                    color = mainTextColor,
                    fontFamily = RelicFontFamily.ubuntu
                )
            )
        },
        onClick = onClick,
        modifier = modifier
            .width(220.dp)
            .height(52.dp),
        icon = {
            Icon(
                painter = painterResource(id = iconResId),
                contentDescription = RelicConstants.ComposeUi.DEFAULT_DESC,
                tint = mainTextColor
            )
        },
        shape = RoundedCornerShape(16.dp),
        backgroundColor = mainThemeColorAccent
    )
}

@Composable
@Preview
private fun ExploreExtendFloatingActionButtonPreview() {
    ExploreExtendFloatingActionButton(
        labelRedId = R.string.explore_label,
        iconResId = R.drawable.ic_person_play,
        onClick = {}
    )
}