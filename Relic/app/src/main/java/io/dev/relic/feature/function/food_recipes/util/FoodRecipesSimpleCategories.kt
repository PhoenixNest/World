package io.dev.relic.feature.function.food_recipes.util

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import io.dev.relic.R

enum class FoodRecipesSimpleCategories(
    @DrawableRes val iconResId: Int,
    @StringRes val tabLabelResId: Int
) {
    Coffee(R.drawable.ic_coffee, R.string.food_recipes_label_coffee),
    Breakfast(R.drawable.ic_breakfast, R.string.food_recipes_label_breakfast),
    Lunch(R.drawable.ic_lunch, R.string.food_recipes_label_lunch),
    Dinner(R.drawable.ic_dinner, R.string.food_recipes_label_dinner),
    Cookie(R.drawable.ic_cookie, R.string.food_recipes_label_cookie),
    Snack(R.drawable.ic_snack, R.string.food_recipes_label_snack)
}