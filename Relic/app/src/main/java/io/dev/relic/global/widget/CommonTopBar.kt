package io.dev.relic.global.widget

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.dev.relic.R
import io.dev.relic.global.RelicConstants.ComposeUi.DEFAULT_DESC
import io.dev.relic.ui.theme.RelicFontFamily
import io.dev.relic.ui.theme.mainTextColor

@Composable
fun CommonTopBar(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerModifier: Modifier = Modifier,
    @StringRes titleResId: Int = -1,
    textColor: Color = mainTextColor,
    hasTitle: Boolean = (titleResId != -1),
    tailContent: @Composable () -> Unit = {}
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
                contentDescription = DEFAULT_DESC
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
                    style = TextStyle(
                        color = textColor,
                        fontSize = 32.sp,
                        fontFamily = RelicFontFamily.fasthand,
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
            tailContent.invoke()
        }
    }
}

@Composable
fun CommonTopBar(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerModifier: Modifier = Modifier,
    title: String = stringResource(id = R.string.app_name),
    textColor: Color = mainTextColor,
    hasTitle: Boolean = false,
    tailContent: @Composable () -> Unit = {}
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
                contentDescription = DEFAULT_DESC
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
                    style = TextStyle(
                        color = textColor,
                        fontSize = 32.sp,
                        fontFamily = RelicFontFamily.fasthand,
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
            tailContent.invoke()
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun CommonTopBarPreview() {
    CommonTopBar(
        onBackClick = {},
        titleResId = R.string.app_name
    )
}

@Composable
@Preview(showBackground = true)
private fun CommonTopBarWithTitlePreview() {
    CommonTopBar(
        onBackClick = {},
        hasTitle = true,
        titleResId = R.string.app_name
    )
}

@Composable
@Preview(showBackground = true)
private fun CommonTopBarWithTitleAndTailPreview() {
    CommonTopBar(
        onBackClick = {},
        hasTitle = true,
        titleResId = R.string.app_name,
        tailContent = {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = DEFAULT_DESC
                )
            }
        }
    )
}