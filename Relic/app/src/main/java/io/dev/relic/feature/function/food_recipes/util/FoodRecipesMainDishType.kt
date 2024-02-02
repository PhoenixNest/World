package io.dev.relic.feature.function.food_recipes.util

import androidx.annotation.StringRes
import io.dev.relic.R

enum class FoodRecipesMainDishType(@StringRes val labelResId: Int) {
    // Auto recommend current type of dish with the current time section.
    Breakfast(R.string.food_recipes_label_breakfast),
    Lunch(R.string.food_recipes_label_lunch),
    Teatime(R.string.food_recipes_label_teatime),
    Dinner(R.string.food_recipes_label_dinner),
    MidnightSnack(R.string.food_recipes_label_midnight_snack),

    // Common dish type
    Recommend(R.string.food_recipes_label_recommend),
    Coffee(R.string.food_recipes_label_coffee),
    Snack(R.string.food_recipes_label_snack)
}
