package io.dev.relic.feature.function.news

import io.data.util.NewsCategory
import io.data.util.NewsCountryType
import io.data.util.NewsLanguageType
import io.data.util.NewsSortRule

object NewsUnitConfig {
    val DEFAULT_NEWS_SORT_RULE = NewsSortRule.PublishedAt
    const val DEFAULT_INIT_NEWS_PAGE_SIZE = 20
    const val DEFAULT_INIT_NEWS_PAGE_INDEX = 1

    object Everything {
        const val DEFAULT_SEARCH_KEYWORDS = "For you"
        val DEFAULT_NEWS_LANGUAGE = NewsLanguageType.EN
    }

    object TopHeadline {
        const val DEFAULT_SEARCH_KEYWORDS = ""
        val DEFAULT_NEWS_CATEGORY = NewsCategory.Technology
        val DEFAULT_NEWS_COUNTRY_TYPE = NewsCountryType.US
    }
}