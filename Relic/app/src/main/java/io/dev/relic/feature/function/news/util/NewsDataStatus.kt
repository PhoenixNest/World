package io.dev.relic.feature.function.news.util

enum class NewsDataStatus(val typeValue: Int) {
    INIT(2),
    SUCCESS(1),
    SUCCESS_WITHOUT_DATA(0),
    FAILED(-1)
}