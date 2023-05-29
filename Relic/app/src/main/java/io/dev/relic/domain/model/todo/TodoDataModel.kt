package io.dev.relic.domain.model.todo

import io.dev.relic.global.utils.TimeUtil

data class TodoDataModel(
    val title: String,
    val subTitle: String,
    val content: String,
    val priority: Int,
    val color: Long,
    val timeStamp: Long,
    val isFinish: Boolean
) {
    companion object {

        fun getTestTodosData(): List<TodoDataModel> {
            val tempList: MutableList<TodoDataModel> = mutableListOf()
            repeat(10) {
                tempList.add(
                    index = it,
                    element = TodoDataModel(
                        title = "Android Jetpack",
                        subTitle = "Compose Ui",
                        content = "Jetpack is a suite of libraries to help developers follow best practices, reduce boilerplate code, and write code that works consistently across Android versions and devices so that developers can focus on the code they care about.",
                        priority = 0,
                        color = 0xFF21313A,
                        timeStamp = TimeUtil.getCurrentTimeInMillis(),
                        isFinish = false
                    )
                )
            }
            return tempList
        }

    }
}