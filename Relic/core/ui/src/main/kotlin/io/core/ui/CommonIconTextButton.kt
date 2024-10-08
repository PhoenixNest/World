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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import io.common.RelicConstants.ComposeUi.DEFAULT_DESC
import io.core.ui.theme.RelicFontFamily.ubuntu

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
    textColor: Color = MaterialTheme.colorScheme.primary,
    iconColor: Color = textColor,
    shape: Shape = RoundedCornerShape(16.dp)
) {
    TextButton(
        onClick = onClick,
        modifier = containerModifier.wrapContentSize(),
        enabled = isEnable,
        shape = shape,
        colors = ButtonDefaults.textButtonColors(
            containerColor = backgroundColor
        )
    ) {
        Row(
            modifier = modifier.wrapContentSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = iconResId),
                contentDescription = DEFAULT_DESC,
                tint = iconColor
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = stringResource(id = labelResId),
                color = textColor,
                style = MaterialTheme.typography.labelMedium
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
    backgroundColor: Color = MaterialTheme.colorScheme.primary
) {
    TextButton(
        onClick = onClick,
        modifier = containerModifier.wrapContentSize(),
        enabled = isEnable,
        colors = ButtonDefaults.textButtonColors(
            containerColor = backgroundColor,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Row(
            modifier = modifier.wrapContentSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = DEFAULT_DESC,
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = stringResource(id = labelResId),
                style = MaterialTheme.typography.labelMedium
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
    textColor: Color = MaterialTheme.colorScheme.primary,
    iconColor: Color = textColor,
    shape: Shape = RoundedCornerShape(16.dp)
) {
    TextButton(
        onClick = onClick,
        modifier = containerModifier.wrapContentSize(),
        enabled = isEnable,
        shape = shape,
        colors = ButtonDefaults.textButtonColors(
            containerColor = backgroundColor
        )
    ) {
        Column(
            modifier = modifier.wrapContentSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = iconResId),
                contentDescription = DEFAULT_DESC,
                tint = iconColor
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = stringResource(id = labelResId),
                color = textColor,
                style = MaterialTheme.typography.labelMedium
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
    textColor: Color = MaterialTheme.colorScheme.primary,
    iconColor: Color = MaterialTheme.colorScheme.primary
) {
    TextButton(
        onClick = onClick,
        modifier = containerModifier.wrapContentSize(),
        enabled = isEnable,
        colors = ButtonDefaults.textButtonColors(
            containerColor = backgroundColor,
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
                contentDescription = DEFAULT_DESC,
                tint = iconColor
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = stringResource(id = labelResId),
                style = TextStyle(
                    color = textColor,
                    fontSize = 12.sp,
                    fontFamily = ubuntu
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