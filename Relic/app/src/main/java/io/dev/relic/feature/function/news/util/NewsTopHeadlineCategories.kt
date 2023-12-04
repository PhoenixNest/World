package io.dev.relic.feature.function.news.util

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import io.dev.relic.R

enum class NewsTopHeadlineCategories(
    @DrawableRes val iconResId: Int,
    @StringRes val tabLabelResId: Int
) {
    Technology(R.drawable.ic_news_technology, R.string.news_label_technology),
    Science(R.drawable.ic_news_science, R.string.news_label_science),
    Business(R.drawable.ic_news_business, R.string.news_label_business),
    Entertainment(R.drawable.ic_news_entertainment, R.string.news_label_entertainment),
    Sports(R.drawable.ic_news_sports, R.string.news_label_sports),
    Health(R.drawable.ic_news_health, R.string.news_label_health)
}