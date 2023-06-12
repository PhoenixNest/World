package io.dev.relic.core.data.database.convertors

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import io.dev.relic.core.data.network.api.dto.food_recipes.complex_search.FoodRecipesComplexSearchDTO

class FoodRecipesDataConvertor {

    @TypeConverter
    fun dataToJson(sourceData: FoodRecipesComplexSearchDTO): String {
        val jsonAdapter: JsonAdapter<FoodRecipesComplexSearchDTO> = Moshi.Builder()
            .build()
            .adapter(FoodRecipesComplexSearchDTO::class.java)
        return jsonAdapter.toJson(sourceData)
    }

    @TypeConverter
    fun jsonToData(json: String): FoodRecipesComplexSearchDTO? {
        val jsonAdapter: JsonAdapter<FoodRecipesComplexSearchDTO> = Moshi.Builder()
            .build()
            .adapter(FoodRecipesComplexSearchDTO::class.java)
        return jsonAdapter.fromJson(json)
    }

}