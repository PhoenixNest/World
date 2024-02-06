package io.dev.relic.feature.function.news.util

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import io.dev.relic.R

enum class NewsTopHeadlineCategories(
    @DrawableRes val iconResId: Int,
    @StringRes val tabLabelResId: Int
) {
    TRENDING(R.drawable.ic_news_trending, R.string.news_label_trending),
    TECHNOLOGY(R.drawable.ic_news_technology, R.string.news_label_technology),
    SCIENCE(R.drawable.ic_news_science, R.string.news_label_science),
    BUSINESS(R.drawable.ic_news_business, R.string.news_label_business),
    ENTERTAINMENT(R.drawable.ic_news_entertainment, R.string.news_label_entertainment),
    SPORTS(R.drawable.ic_news_sports, R.string.news_label_sports),
    HEALTH(R.drawable.ic_news_health, R.string.news_label_health)
}