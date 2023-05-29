package io.dev.relic.feature.main.unit.todo.subpage.create

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.dev.relic.R
import io.dev.relic.feature.main.unit.todo.subpage.create.widget.CreateTodoPageTopBar
import io.dev.relic.global.widget.CommonInputField

@Composable
fun CreateTodoPageRoute(
    onBackClick: () -> Unit,
    onFinishClick: () -> Unit
) {
    CreateTodoPage(
        onBackClick = onBackClick,
        onFinishClick = onFinishClick
    )
}

@Composable
private fun CreateTodoPage(
    onBackClick: () -> Unit,
    onFinishClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    var title: String by remember {
        mutableStateOf("")
    }

    var subTitle: String by remember {
        mutableStateOf("")
    }

    var content: String by remember {
        mutableStateOf("")
    }

    Scaffold(
        topBar = {
            CreateTodoPageTopBar(
                onBackClick = onBackClick,
                onFinishClick = onFinishClick,
                title = title
            )
        }
    ) { paddingValues: PaddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CommonInputField(
                    content = title,
                    hintResId = R.string.todo_create_title_hint,
                    onValueChange = { inputValue: String ->
                        title = inputValue
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color.LightGray.copy(alpha = 0.3F),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(20.dp),
                    textStyle = TextStyle(fontSize = 32.sp)
                )
            }
            Box(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CommonInputField(
                    content = subTitle,
                    hintResId = R.string.todo_create_subtitle_hint,
                    onValueChange = { inputValue: String ->
                        subTitle = inputValue
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color.LightGray.copy(alpha = 0.3F),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(20.dp),
                    textStyle = TextStyle(fontSize = 20.sp)
                )
            }
            Box(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CommonInputField(
                    content = content,
                    hintResId = R.string.todo_create_content_hint,
                    onValueChange = { inputValue: String ->
                        content = inputValue
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = Color.LightGray.copy(alpha = 0.3F),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(20.dp)
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun CreateTodoPagePreview() {
    CreateTodoPage(
        onBackClick = {},
        onFinishClick = {}
    )
}
