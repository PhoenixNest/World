package io.dev.relic.feature.function.food_recipes.util

import io.common.util.TimeUtil.TimeSection
import io.common.util.TimeUtil.TimeSection.Afternoon
import io.common.util.TimeUtil.TimeSection.Day
import io.common.util.TimeUtil.TimeSection.MidNight
import io.common.util.TimeUtil.TimeSection.Night
import io.common.util.TimeUtil.TimeSection.Noon
import io.common.util.TimeUtil.TimeSection.Unknown
import io.dev.relic.feature.function.food_recipes.util.FoodRecipesMainDishType.Breakfast
import io.dev.relic.feature.function.food_recipes.util.FoodRecipesMainDishType.Dinner
import io.dev.relic.feature.function.food_recipes.util.FoodRecipesMainDishType.Lunch
import io.dev.relic.feature.function.food_recipes.util.FoodRecipesMainDishType.MidnightSnack
import io.dev.relic.feature.function.food_recipes.util.FoodRecipesMainDishType.Recommend
import io.dev.relic.feature.function.food_recipes.util.FoodRecipesMainDishType.Teatime

object FoodRecipesAutoConvertor {

    fun convertTimeSectionToDishType(timeSection: TimeSection): FoodRecipesMainDishType {
        return when (timeSection) {
            Day -> Breakfast
            Noon -> Lunch
            Afternoon -> Teatime
            Night -> Dinner
            MidNight -> MidnightSnack
            Unknown -> Recommend
        }
    }
}