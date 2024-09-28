package io.dev.relic.feature.function.todo.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.common.RelicConstants.ComposeUi.DEFAULT_DESC
import io.core.ui.theme.mainIconColorLight
import io.core.ui.theme.mainThemeColorLight
import io.dev.relic.R

@Composable
fun TodoTailItem(
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .width(320.dp)
            .height(240.dp),
        shape = RoundedCornerShape(16.dp),
        color = mainThemeColorLight.copy(alpha = 0.1F)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable { onItemClick.invoke() }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = DEFAULT_DESC,
                modifier = Modifier.align(Alignment.Center),
                tint = mainIconColorLight
            )
        }
    }
}

@Composable
@Preview
private fun TodoTailItemPreview() {
    TodoTailItem(onItemClick = {})
}