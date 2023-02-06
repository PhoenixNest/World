package io.dev.android.composer.jetpack.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
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
}