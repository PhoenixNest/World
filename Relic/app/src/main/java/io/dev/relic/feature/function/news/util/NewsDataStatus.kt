package io.dev.relic.feature.function.news.util

enum class NewsDataStatus(val typeValue: Int) {
    Init(2),
    Success(1),
    SuccessWithoutData(0),
    Failed(-1)
}