package io.dev.android.composer.jetpack.viewmodel

import android.app.Application
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.dev.android.composer.jetpack.model.StateChangeTestModel
import io.dev.android.composer.jetpack.model.chat.Message
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    var messageData: List<Message> = emptyList()

    fun fetchMessageData() {
        messageData = Message.testConversation()
    }

    private val _testStateListData = StateChangeTestModel.testStateListData().toMutableStateList()
    val testStateListData: List<StateChangeTestModel>
        get() = _testStateListData

    fun changeTestStateItemChecked(
        item: StateChangeTestModel,
        checked: Boolean
    ) {
        _testStateListData.find { it.id == item.id }?.let { model ->
            model.checked = checked
        }
    }

    fun removeTestStateListItem(item: StateChangeTestModel) {
        _testStateListData.remove(item)
    }
}