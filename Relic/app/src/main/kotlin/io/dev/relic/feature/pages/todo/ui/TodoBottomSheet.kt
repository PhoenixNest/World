package io.dev.relic.feature.pages.todo.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.dev.relic.feature.function.todo.util.TodoPriority

@Composable
fun TodoBottomSheet(
    sortRule: TodoPriority,
    modifier: Modifier = Modifier
) {

}

@Composable
private fun TodoBottomSheetPreview() {
    TodoBottomSheet(sortRule = TodoPriority.NORMAL)
}