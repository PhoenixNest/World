package io.dev.relic.global.widget

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.dev.relic.R
import io.dev.relic.global.RelicConstants
import io.dev.relic.ui.theme.RelicFontFamily
import io.dev.relic.ui.theme.mainTextColor

@Composable
fun CommonIconTextButton(
    @DrawableRes iconResId: Int,
    @StringRes labelResId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerModifier: Modifier = Modifier,
    isEnable: Boolean = true,
    backgroundColor: Color = Color.Transparent,
    textColor: Color = mainTextColor,
    iconColor: Color = mainTextColor
) {
    TextButton(
        onClick = onClick,
        modifier = containerModifier,
        enabled = isEnable,
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = backgroundColor
        )
    ) {
        Row(
            modifier = modifier.padding(
                horizontal = 12.dp,
                vertical = 8.dp
            ),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = iconResId),
                contentDescription = RelicConstants.ComposeUi.DEFAULT_DESC,
                tint = iconColor
            )
            Spacer(modifier = modifier.width(6.dp))
            Text(
                text = stringResource(id = labelResId),
                style = TextStyle(
                    color = textColor,
                    fontSize = 12.sp,
                    fontFamily = RelicFontFamily.ubuntu
                )
            )
        }
    }
}

@Composable
fun CommonIconTextButton(
    icon: ImageVector,
    @StringRes labelResId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerModifier: Modifier = Modifier,
    isEnable: Boolean = true,
    backgroundColor: Color = Color.Transparent,
    textColor: Color = mainTextColor,
    iconColor: Color = mainTextColor
) {
    TextButton(
        onClick = onClick,
        modifier = containerModifier,
        enabled = isEnable,
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = backgroundColor,
            contentColor = Color.LightGray
        )
    ) {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = RelicConstants.ComposeUi.DEFAULT_DESC,
                tint = iconColor
            )
            Spacer(modifier = modifier.width(6.dp))
            Text(
                text = stringResource(id = labelResId),
                style = TextStyle(
                    color = textColor,
                    fontSize = 12.sp,
                    fontFamily = RelicFontFamily.ubuntu
                )
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun CommonIconTextPreview() {
    CommonIconTextButton(
        icon = Icons.Default.Check,
        labelResId = R.string.app_name,
        onClick = {}
    )
}