package io.data.util

/**
 * The order to sort the articles in.
 *
 * `Possible options: relevancy, popularity, publishedAt`.
 *
 * - relevancy = articles more closely related to q come first.
 * - popularity = articles from popular sources and publishers come first.
 * - publishedAt = newest articles come first.*/
enum class NewsSortRule(val ruleString: String) {
    RELEVANCY("relevancy"),
    POPULARITY("popularity"),
    PUBLISHED_AT("publishAt")
}