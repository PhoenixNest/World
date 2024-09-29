package io.core.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.common.RelicConstants.ComposeUi.DEFAULT_DESC
import io.core.ui.theme.RelicFontFamily.newsReader
import io.core.ui.theme.RelicFontFamily.ubuntu
import io.core.ui.theme.mainIconColorLight
import io.core.ui.theme.mainTextColor

@Composable
fun CommonTopBar(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerModifier: Modifier = Modifier,
    @StringRes titleResId: Int = -1,
    textColor: Color = mainTextColor,
    iconColor: Color = mainIconColorLight,
    hasTitle: Boolean = (titleResId != -1),
    tailContent: @Composable BoxScope.() -> Unit = {}
) {
    Row(
        modifier = containerModifier
            .fillMaxWidth()
            .height(56.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Back button
        IconButton(
            onClick = onBackClick,
            modifier = modifier
                .fillMaxSize()
                .weight(1F)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = DEFAULT_DESC,
                tint = iconColor
            )
        }
        // Title
        Box(
            modifier = modifier
                .fillMaxSize()
                .weight(3F),
            contentAlignment = Alignment.Center
        ) {
            if (hasTitle) {
                Text(
                    text = stringResource(id = titleResId),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = TextStyle(
                        color = textColor,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = ubuntu,
                        textAlign = TextAlign.Center
                    )
                )
            }
        }
        // Tail content, such as: menu, balloon window, popup window
        Box(
            modifier = modifier
                .fillMaxSize()
                .weight(1F),
            contentAlignment = Alignment.Center
        ) {
            tailContent.invoke(this@Box)
        }
    }
}

@Composable
fun CommonTopBar(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerModifier: Modifier = Modifier,
    hasTitle: Boolean = false,
    title: String = stringResource(id = R.string.app_name),
    textColor: Color = mainTextColor,
    iconColor: Color = mainIconColorLight,
    tailContent: @Composable BoxScope.() -> Unit = {}
) {
    Row(
        modifier = containerModifier
            .fillMaxWidth()
            .height(56.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Back button
        IconButton(
            onClick = onBackClick,
            modifier = modifier
                .fillMaxSize()
                .weight(1F)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = DEFAULT_DESC,
                tint = iconColor
            )
        }
        // Title
        Box(
            modifier = modifier
                .fillMaxSize()
                .weight(3F),
            contentAlignment = Alignment.Center
        ) {
            if (hasTitle) {
                Text(
                    text = title,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = TextStyle(
                        color = textColor,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = newsReader,
                        textAlign = TextAlign.Center
                    )
                )
            }
        }
        // Tail content, such as: menu, balloon window, popup window
        Box(
            modifier = modifier
                .fillMaxSize()
                .weight(1F),
            contentAlignment = Alignment.Center
        ) {
            tailContent.invoke(this@Box)
        }
    }
}

@Composable
@Preview
private fun CommonTopBarPreview() {
    CommonTopBar(
        onBackClick = {},
        titleResId = R.string.app_name
    )
}

@Composable
@Preview
private fun CommonTopBarWithTitlePreview() {
    CommonTopBar(
        onBackClick = {},
        hasTitle = true,
        titleResId = R.string.app_name
    )
}

@Composable
@Preview
private fun CommonTopBarWithTitleAndTailPreview() {
    CommonTopBar(
        onBackClick = {},
        hasTitle = true,
        titleResId = R.string.app_name,
        tailContent = {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = DEFAULT_DESC,
                    tint = mainIconColorLight
                )
            }
        }
    )
}