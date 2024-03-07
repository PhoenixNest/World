package io.data.util

object NewsConfig {

    val DEFAULT_NEWS_SORT_RULE = NewsSortRule.PUBLISHED_AT
    const val DEFAULT_INIT_NEWS_PAGE_SIZE = 20
    const val DEFAULT_INIT_NEWS_PAGE_INDEX = 1
    const val DEFAULT_KEY_WORDS = "trending"

    object Trending {
        const val DEFAULT_SEARCH_KEYWORDS = DEFAULT_KEY_WORDS
        const val DEFAULT_NEWS_SOURCE = ""
        val DEFAULT_NEWS_LANGUAGE = NewsLanguageType.EN
    }

    object TopHeadline {
        const val DEFAULT_SEARCH_KEYWORDS = DEFAULT_KEY_WORDS
        val DEFAULT_NEWS_COUNTRY_TYPE = NewsCountryType.US
        val DEFAULT_NEWS_CATEGORY = NewsCategory.TECHNOLOGY
    }
}