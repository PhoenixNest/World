package io.dev.relic.feature.function.todo.util

enum class TodoSortRule(ruleId: Int) {
    Date(0),
    Alphabet(1),
    Modified(2),
    Priority(3)
}