package io.data.mappers

import io.data.entity.todo.TodoEntity
import io.data.model.todo.TodoDataModel

object TodoDataMapper {

    fun List<TodoEntity>?.toModelList(): List<TodoDataModel> {
        val tempList = mutableListOf<TodoDataModel>()
        if (this.isNullOrEmpty()) {
            return tempList
        }

        this.forEach {
            tempList.add(it.toModel())
        }

        return tempList
    }

    private fun TodoEntity.toModel(): TodoDataModel {
        return TodoDataModel(
            title = title,
            subtitle = subtitle,
            content = content,
            priority = priority,
            color = color,
            updateTime = updateTime,
            isFinish = isFinish
        )
    }

}