package io.dev.android.composer.jetpack.ui.home.state_change_list

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.dev.android.composer.jetpack.model.StateChangeTestModel

@Composable
fun StateList(
    dataList: List<StateChangeTestModel>,
    modifier: Modifier = Modifier,
    onCheckChange: (StateChangeTestModel, Boolean) -> Unit,
    onItemClick: (StateChangeTestModel) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        state = rememberLazyListState()
    ) {
        items(
            items = dataList,
            key = { model ->
                model.id
            }
        ) { model ->
            StateListItem(
                title = model.title,
                checked = model.checked,
                onCheckChange = { checked ->
                    onCheckChange(model, checked)
                },
                onItemClick = {
                    onItemClick(model)
                }
            )
        }
    }
}