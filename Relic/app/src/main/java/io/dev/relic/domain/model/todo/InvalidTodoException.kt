package io.dev.relic.domain.model.todo

/**
 * Todo exception information class.
 *
 * @param message       Error message
 * */
class InvalidTodoException(
    message: String
) : Exception(message = message)