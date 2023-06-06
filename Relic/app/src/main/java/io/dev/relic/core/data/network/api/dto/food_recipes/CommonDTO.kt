package io.dev.relic.core.data.network.api.dto.food_recipes

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class ExtendedIngredient(
    @Json(name = "id")
    val id: Int,
    @Json(name = "aisle")
    val aisle: String,
    @Json(name = "image")
    val image: String,
    @Json(name = "consistency")
    val consistency: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "nameClean")
    val nameClean: String,
    @Json(name = "original")
    val original: String,
    @Json(name = "originalName")
    val originalName: String,
    @Json(name = "amount")
    val amount: Double,
    @Json(name = "unit")
    val unit: String,
    @Json(name = "meta")
    val meta: List<String>,
    @Json(name = "measures")
    val measures: Measures
)

@JsonClass(generateAdapter = true)
data class Measures(
    @Json(name = "us")
    val us: Us,
    @Json(name = "metric")
    val metric: Metric
)

@JsonClass(generateAdapter = true)
data class Us(
    @Json(name = "amount")
    val amount: Double,
    @Json(name = "unitShort")
    val unitShort: String,
    @Json(name = "unitLong")
    val unitLong: String
)

@JsonClass(generateAdapter = true)
data class Metric(
    @Json(name = "amount")
    val amount: Double,
    @Json(name = "unitShort")
    val unitShort: String,
    @Json(name = "unitLong")
    val unitLong: String
)

@JsonClass(generateAdapter = true)
data class AnalyzedInstruction(
    @Json(name = "name")
    val name: String,
    @Json(name = "steps")
    val steps: List<Step>
)

@JsonClass(generateAdapter = true)
data class Step(
    @Json(name = "number")
    val number: Int,
    @Json(name = "step")
    val step: String,
    @Json(name = "ingredients")
    val ingredients: List<Ingredient>,
    @Json(name = "equipment")
    val equipment: List<Equipment>
)

@JsonClass(generateAdapter = true)
data class Ingredient(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "localizedName")
    val localizedName: String,
    @Json(name = "image")
    val image: String
)

@JsonClass(generateAdapter = true)
data class Equipment(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "localizedName")
    val localizedName: String,
    @Json(name = "image")
    val image: String
)

@JsonClass(generateAdapter = true)
data class WinePairing(
    @Json(name = "pairedWines")
    val pairedWines: List<Any>,
    @Json(name = "pairingText")
    val pairingText: String,
    @Json(name = "productMatches")
    val productMatches: List<Any>
)