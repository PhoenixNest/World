package io.dev.relic.core.data.database.convertors

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import io.dev.relic.core.data.network.api.dto.food_recipes.complex_search.FoodRecipesComplexSearchDTO

class FoodRecipesDataConvertor {

    @TypeConverter
    fun dataToJson(sourceData: FoodRecipesComplexSearchDTO): String {
        return Moshi.Builder()
            .build()
            .adapter(FoodRecipesComplexSearchDTO::class.java)
            .toJson(sourceData)
    }

    @TypeConverter
    fun jsonToData(json: String): FoodRecipesComplexSearchDTO? {
        return Moshi.Builder()
            .build()
            .adapter(FoodRecipesComplexSearchDTO::class.java)
            .fromJson(json)
    }

}