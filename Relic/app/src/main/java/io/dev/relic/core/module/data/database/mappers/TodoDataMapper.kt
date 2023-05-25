package io.dev.relic.core.module.data.database.mappers

import io.dev.relic.core.module.data.database.entity.TodoEntity
import io.dev.relic.domain.model.todo.TodoDataModel

/**
 * Convert Todo entity to data model.
 * */
object TodoDataMapper {

    fun TodoEntity.toTodoDataModel(): TodoDataModel {
        return TodoDataModel(
            title = title,
            subTitle = subTitle,
            content = content,
            color = color,
            timeStamp = timeStamp
        )
    }

}