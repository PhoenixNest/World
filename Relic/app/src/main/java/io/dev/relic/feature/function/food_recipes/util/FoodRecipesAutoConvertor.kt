package io.dev.relic.feature.function.food_recipes.util

import io.common.util.TimeUtil.TimeSection
import io.common.util.TimeUtil.TimeSection.AFTERNOON
import io.common.util.TimeUtil.TimeSection.DAY
import io.common.util.TimeUtil.TimeSection.MIDNIGHT
import io.common.util.TimeUtil.TimeSection.NIGHT
import io.common.util.TimeUtil.TimeSection.NOON
import io.common.util.TimeUtil.TimeSection.UNKNOWN
import io.dev.relic.feature.function.food_recipes.util.FoodRecipesMainDishType.BREAKFAST
import io.dev.relic.feature.function.food_recipes.util.FoodRecipesMainDishType.DINNER
import io.dev.relic.feature.function.food_recipes.util.FoodRecipesMainDishType.LUNCH
import io.dev.relic.feature.function.food_recipes.util.FoodRecipesMainDishType.MIDNIGHT_SNACK
import io.dev.relic.feature.function.food_recipes.util.FoodRecipesMainDishType.RECOMMEND
import io.dev.relic.feature.function.food_recipes.util.FoodRecipesMainDishType.TEATIME

object FoodRecipesAutoConvertor {

    fun convertTimeSectionToDishType(timeSection: TimeSection): FoodRecipesMainDishType {
        return when (timeSection) {
            DAY -> BREAKFAST
            NOON -> LUNCH
            AFTERNOON -> TEATIME
            NIGHT -> DINNER
            MIDNIGHT -> MIDNIGHT_SNACK
            UNKNOWN -> RECOMMEND
        }
    }
}