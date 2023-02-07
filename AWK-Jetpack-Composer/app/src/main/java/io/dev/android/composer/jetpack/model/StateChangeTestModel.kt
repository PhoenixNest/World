package io.dev.android.composer.jetpack.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class StateChangeTestModel(
    val id: Int,
    val title: String,
    initialChecked: Boolean = false
) {
    var checked by mutableStateOf(initialChecked)

    companion object {
        fun testStateListData(): List<StateChangeTestModel> {
            return List(30) { i ->
                StateChangeTestModel(i, "State List Item: $i")
            }
        }
    }
}