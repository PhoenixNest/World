package io.dev.relic.feature.main.unit.todo.widget

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
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Sort
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.dev.relic.R
import io.dev.relic.global.RelicConstants.ComposeUi.DEFAULT_DESC
import io.dev.relic.ui.theme.RelicFontFamily
import io.dev.relic.ui.theme.mainTextColor

@Composable
fun TodoPageTopBar(
    onBackClick: () -> Unit,
    onSortClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TopBarHeader(
            onBackClick = onBackClick,
            modifier = modifier.weight(1F)
        )
        TopBarContent(
            modifier = modifier.weight(2F)
        )
        TopBarTail(
            onSortClick = onSortClick,
            modifier = modifier.weight(1F)
        )
    }
}

@Composable
private fun TopBarHeader(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = DEFAULT_DESC,
                tint = Color.DarkGray
            )
        }
    }
}

@Composable
private fun TopBarContent(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = R.string.todo_title),
            style = TextStyle(
                color = mainTextColor,
                fontSize = 32.sp,
                fontFamily = RelicFontFamily.fasthand
            )
        )
    }
}

@Composable
private fun TopBarTail(
    onSortClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        IconButton(onClick = onSortClick) {
            Icon(
                imageVector = Icons.Rounded.Sort,
                contentDescription = DEFAULT_DESC,
                tint = Color.DarkGray
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun TodoPageTopBarPreview() {
    TodoPageTopBar(
        onBackClick = {},
        onSortClick = {}
    )
}