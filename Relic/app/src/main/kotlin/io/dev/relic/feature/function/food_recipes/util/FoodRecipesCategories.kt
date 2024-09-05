package io.dev.relic.feature.function.food_recipes.util

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import io.dev.relic.R

enum class FoodRecipesCategories(
    @DrawableRes val iconResId: Int,
    @StringRes val tabLabelResId: Int
) {
    COFFEE(R.drawable.ic_coffee, R.string.food_recipes_label_coffee),
    COOKIE(R.drawable.ic_cookie, R.string.food_recipes_label_cookie),
    SNACK(R.drawable.ic_snack, R.string.food_recipes_label_snack)
}