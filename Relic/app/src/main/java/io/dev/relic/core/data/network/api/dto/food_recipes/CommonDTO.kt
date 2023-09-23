package io.dev.relic.core.data.network.api.dto.food_recipes

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Us(
    @Json(name = "amount")
    val amount: Double?,
    @Json(name = "unitShort")
    val unitShort: String?,
    @Json(name = "unitLong")
    val unitLong: String?
)

@JsonClass(generateAdapter = true)
data class Measures(
    @Json(name = "us")
    val us: Us?,
    @Json(name = "metric")
    val metric: Metric?
)

@JsonClass(generateAdapter = true)
data class Metric(
    @Json(name = "amount")
    val amount: Double?,
    @Json(name = "unitShort")
    val unitShort: String?,
    @Json(name = "unitLong")
    val unitLong: String?
)

@JsonClass(generateAdapter = true)
data class NutrientItem(
    @Json(name = "name")
    val name: String?,
    @Json(name = "amount")
    val amount: Double?,
    @Json(name = "unit")
    val unit: String?,
    @Json(name = "percentOfDailyNeeds")
    val percentOfDailyNeeds: Double?
)