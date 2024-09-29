package io.dev.relic.feature.function.food_recipes.util

import androidx.annotation.StringRes
import io.dev.relic.R

enum class FoodRecipesMainDishType(@StringRes val labelResId: Int) {
    // Auto recommend current type of dish with the current time section.
    BREAKFAST(R.string.food_recipes_label_breakfast),
    LUNCH(R.string.food_recipes_label_lunch),
    TEATIME(R.string.food_recipes_label_teatime),
    DINNER(R.string.food_recipes_label_dinner),
    MIDNIGHT_SNACK(R.string.food_recipes_label_midnight_snack),

    // Common dish type
    RECOMMEND(R.string.food_recipes_label_recommend),
    COFFEE(R.string.food_recipes_label_coffee),
    SNACK(R.string.food_recipes_label_snack)
}
