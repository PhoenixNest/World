package io.data.model.todo

data class TodoDataModel(
    val title: String,
    val subtitle: String,
    val content: String,
    val priority: Int,
    val updateTime: String,
    val isFinish: Boolean
)