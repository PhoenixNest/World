package io.dev.android.composer.jetpack.ui.home.state_change_list

import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import io.dev.android.composer.jetpack.model.StateChangeTestModel

@Preview(showBackground = true)
@Composable
fun StateListItemPreview() {
    var checkedState by remember {
        mutableStateOf(false)
    }

    StateListItem(
        title = "State List Item 1",
        checked = checkedState,
        onCheckChange = {
            checkedState = !checkedState
        },
        onItemClick = {

        }
    )
}

@Preview(showBackground = true)
@Composable
fun StateListPreview() {
    StateList(
        dataList = StateChangeTestModel.testStateListData(),
        onItemClick = {

        },
        onCheckChange = { model: StateChangeTestModel, check: Boolean ->

        },
    )
}