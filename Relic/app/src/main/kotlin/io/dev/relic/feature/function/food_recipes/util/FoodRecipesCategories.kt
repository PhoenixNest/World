package io.dev.relic.feature.function.food_recipes.util

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import io.dev.relic.R

enum class FoodRecipesCategories(
    @DrawableRes val iconResId: Int,
    @StringRes val tabLabelResId: Int
) {
    BREAKFAST(R.drawable.ic_breakfast, R.string.food_recipes_label_breakfast),
    LUNCH(R.drawable.ic_lunch, R.string.food_recipes_label_lunch),
    DINNER(R.drawable.ic_dinner, R.string.food_recipes_label_dinner),
    COFFEE(R.drawable.ic_coffee, R.string.food_recipes_label_coffee),
    COOKIE(R.drawable.ic_cookie, R.string.food_recipes_label_cookie),
    SNACK(R.drawable.ic_snack, R.string.food_recipes_label_snack)
}