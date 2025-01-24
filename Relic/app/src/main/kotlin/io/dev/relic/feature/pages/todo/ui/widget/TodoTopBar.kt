package io.dev.relic.feature.pages.todo.ui.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.common.RelicConstants.ComposeUi.DEFAULT_DESC
import io.core.ui.theme.RelicAppTheme
import io.core.ui.theme.RelicFontFamily.googleSans
import io.dev.relic.R

@Stable
data class TodoTopBarAction(
    val onBackClick: () -> Unit
)

@Composable
fun TodoTopBar(
    action: TodoTopBarAction,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surfaceContainer
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
        ) {
            IconButton(
                onClick = action.onBackClick,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    painter = painterResource(io.core.ui.R.drawable.ic_back),
                    contentDescription = DEFAULT_DESC,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
            Text(
                text = stringResource(R.string.todo_tasks_title),
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.onSurface,
                fontFamily = googleSans,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun TodoTopBarPreview() {
    RelicAppTheme {
        TodoTopBar(
            action = TodoTopBarAction(
                onBackClick = {}
            )
        )
    }
}