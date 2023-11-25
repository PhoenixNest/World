package io.core.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.common.RelicConstants
import io.core.ui.R
import io.core.ui.theme.RelicFontFamily
import io.core.ui.theme.mainTextColor

/* ======================== Horizontal ======================== */

@Composable
fun CommonHorizontalIconTextButton(
    @DrawableRes iconResId: Int,
    @StringRes labelResId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerModifier: Modifier = Modifier,
    isEnable: Boolean = true,
    backgroundColor: Color = Color.Transparent,
    textColor: Color = mainTextColor,
    iconColor: Color = textColor,
    shape: Shape = RoundedCornerShape(16.dp)
) {
    TextButton(
        onClick = onClick,
        modifier = containerModifier.wrapContentSize(),
        enabled = isEnable,
        shape = shape,
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = backgroundColor
        )
    ) {
        Row(
            modifier = modifier.wrapContentSize(),
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
fun CommonHorizontalIconTextButton(
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
        modifier = containerModifier.wrapContentSize(),
        enabled = isEnable,
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = backgroundColor,
            contentColor = Color.LightGray
        )
    ) {
        Row(
            modifier = modifier.wrapContentSize(),
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
@Preview
private fun CommonHorizontalIconTextButtonPreview() {
    CommonHorizontalIconTextButton(
        icon = Icons.Default.Check,
        labelResId = R.string.app_name,
        onClick = {}
    )
}

/* ======================== Vertical ======================== */

@Composable
fun CommonVerticalIconTextButton(
    @DrawableRes iconResId: Int,
    @StringRes labelResId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerModifier: Modifier = Modifier,
    isEnable: Boolean = true,
    backgroundColor: Color = Color.Transparent,
    textColor: Color = mainTextColor,
    iconColor: Color = textColor,
    shape: Shape = RoundedCornerShape(16.dp)
) {
    TextButton(
        onClick = onClick,
        modifier = containerModifier.wrapContentSize(),
        enabled = isEnable,
        shape = shape,
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = backgroundColor
        )
    ) {
        Column(
            modifier = modifier.wrapContentSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = iconResId),
                contentDescription = RelicConstants.ComposeUi.DEFAULT_DESC,
                tint = iconColor
            )
            Spacer(modifier = modifier.height(6.dp))
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
fun CommonVerticalIconTextButton(
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
        modifier = containerModifier.wrapContentSize(),
        enabled = isEnable,
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = backgroundColor,
            contentColor = Color.LightGray
        )
    ) {
        Column(
            modifier = modifier.wrapContentSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = RelicConstants.ComposeUi.DEFAULT_DESC,
                tint = iconColor
            )
            Spacer(modifier = modifier.height(6.dp))
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
@Preview
private fun CommonVerticalIconTextButtonPreview() {
    CommonVerticalIconTextButton(
        icon = Icons.Default.Check,
        labelResId = R.string.app_name,
        onClick = {}
    )
}