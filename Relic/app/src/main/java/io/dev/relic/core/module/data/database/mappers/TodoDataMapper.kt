package io.dev.relic.core.module.data.database.mappers

import io.dev.relic.core.module.data.database.entity.TodoEntity
import io.dev.relic.domain.model.todo.TodoDataModel
import io.dev.relic.global.utils.LogUtil

/**
 * Convert Todo entity to data model.
 * */
object TodoDataMapper {

    private const val TAG = "TodoDataMapper"

    fun List<TodoEntity>.toTodoDataList(): List<TodoDataModel> {
        val tempList: MutableList<TodoDataModel> = mutableListOf()
        forEach { todoEntity: TodoEntity ->
            LogUtil.debug(TAG, "[Convert EntityList to ModelList] currentEntity: $todoEntity")
            tempList.add(todoEntity.toTodoDataModel())
        }
        return tempList
    }

    private fun TodoEntity.toTodoDataModel(): TodoDataModel {
        return TodoDataModel(
            title = title,
            subTitle = subTitle,
            content = content,
            color = color,
            timeStamp = timeStamp
        )
    }

}