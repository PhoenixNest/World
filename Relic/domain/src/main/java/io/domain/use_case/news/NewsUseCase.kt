package io.domain.use_case.news

import io.domain.use_case.news.action.FetchEverythingNews
import io.domain.use_case.news.action.FetchHeadlineNews

internal const val TAG = "NewsUseCase"

data class NewsUseCase(
    val fetchEverythingNews: FetchEverythingNews,
    val fetchTopHeadlineNews: FetchHeadlineNews
)