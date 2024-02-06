package io.dev.relic.feature.function.news

import io.data.util.NewsCategory
import io.data.util.NewsCountryType
import io.data.util.NewsLanguageType
import io.data.util.NewsSortRule
import io.dev.relic.feature.function.news.util.NewsTopHeadlineCategories

object NewsUnitConfig {

    val DEFAULT_NEWS_SORT_RULE = NewsSortRule.PUBLISHED_AT
    const val DEFAULT_INIT_NEWS_PAGE_SIZE = 20
    const val DEFAULT_INIT_NEWS_PAGE_INDEX = 1

    private const val DEFAULT_KEY_WORDS = "Trending"
    private const val DEFAULT_NEWS_SOURCE = ""

    object Everything {
        const val DEFAULT_SEARCH_KEYWORDS = DEFAULT_KEY_WORDS
        const val DEFAULT_NEWS_SOURCE = NewsUnitConfig.DEFAULT_NEWS_SOURCE
        val DEFAULT_NEWS_LANGUAGE = NewsLanguageType.EN
    }

    object TopHeadline {
        val DEFAULT_SEARCH_KEYWORDS = NewsTopHeadlineCategories.TRENDING.name
        val DEFAULT_NEWS_COUNTRY_TYPE = NewsCountryType.US
        val DEFAULT_NEWS_CATEGORY = NewsCategory.Technology
    }
}