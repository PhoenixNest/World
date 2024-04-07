package io.dev.relic.feature.pages.studio.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
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
import io.core.ui.theme.RelicFontFamily.ubuntu
import io.core.ui.theme.mainButtonColorLight
import io.core.ui.theme.mainTextColor
import io.core.ui.theme.mainThemeColorAccent
import io.core.ui.theme.mainThemeColorLight
import io.data.model.todo.TodoDataModel
import io.dev.relic.R
import io.dev.relic.feature.function.todo.TodoDataState
import io.dev.relic.feature.function.todo.ui.TodoPanel
import io.dev.relic.feature.pages.studio.StudioTodoListState
import io.dev.relic.feature.pages.studio.StudioTotoState

@Composable
fun StudioPageContent(
    todoState: StudioTotoState
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = mainThemeColorAccent
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            StudioPageTabBar(onUserClick = {})
            // StudioTodoPanel(
            //     todoState = todoState,
            //     onItemClick = {}
            // )
            // TODO: Add todo panel
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(horizontal = 16.dp)
                    .background(
                        color = mainThemeColorLight.copy(alpha = 0.6F),
                        shape = RoundedCornerShape(16.dp)
                    )
            )
            Spacer(modifier = Modifier.height(16.dp))
            // TODO: Consider add the commerce widget, such as gold price
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(horizontal = 16.dp)
                    .background(
                        color = mainThemeColorLight.copy(alpha = 0.6F),
                        shape = RoundedCornerShape(16.dp)
                    )
            )
        }
    }
}

@Composable
private fun StudioTodoPanel(
    todoState: StudioTotoState,
    onItemClick: (data: TodoDataModel) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        when (val state = todoState.todoDataState) {
            is TodoDataState.Init,
            is TodoDataState.Querying -> {
                //
            }

            is TodoDataState.NoTodoData,
            is TodoDataState.Empty -> {
                //
            }

            is TodoDataState.QuerySucceed -> {
                TodoPanel(
                    modelList = state.modelList,
                    onItemClick = onItemClick,
                    lazyListState = todoState.listState.todoListState
                )
            }

            is TodoDataState.QueryFailed -> {

            }
        }
    }
}

@Composable
private fun StudioPageTabBar(
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
                fontFamily = ubuntu,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
@Preview
private fun StudioPageContentPreview() {
    StudioPageContent(
        todoState = StudioTotoState(
            todoDataState = TodoDataState.Init,
            listState = StudioTodoListState(
                todoListState = rememberLazyListState()
            )
        )
    )
}