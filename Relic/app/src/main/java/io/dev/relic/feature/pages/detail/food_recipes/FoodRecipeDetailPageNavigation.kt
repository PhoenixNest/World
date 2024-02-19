package io.dev.relic.feature.pages.detail.food_recipes

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.compose.composable
import io.common.RelicConstants.Common.UNKNOWN_VALUE_INT
import io.dev.relic.feature.function.food_recipes.viewmodel.FoodRecipesViewModel
import io.dev.relic.feature.route.RelicRoute.DETAIL_FOOD_RECIPE

private const val KEY_FOOD_RECIPE_ID = "keys_food_recipe_id"

@SuppressLint("RestrictedApi")
fun NavController.navigateToFoodRecipeDetailPage(
    recipeId: Int,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) {
    val deepLinkRequest = NavDeepLinkRequest.Builder.fromUri(
        NavDestination.createRoute(DETAIL_FOOD_RECIPE).toUri()
    ).build()

    graph.matchDeepLink(deepLinkRequest)?.let { deepLinkMatch ->
        val bundle = Bundle().apply {
            putInt(KEY_FOOD_RECIPE_ID, recipeId)
        }

        this.navigate(
            resId = deepLinkMatch.destination.id,
            args = bundle,
            navOptions = navOptions,
            navigatorExtras = navigatorExtras
        )
    }
}

fun NavGraphBuilder.pageFoodRecipeDetail(
    foodRecipesViewModel: FoodRecipesViewModel,
    onBackClick: () -> Unit
) {
    composable(route = DETAIL_FOOD_RECIPE) {
        it.arguments?.apply {
            val id = getInt(KEY_FOOD_RECIPE_ID, UNKNOWN_VALUE_INT)
            FoodRecipeDetailPageRoute(
                recipeId = id,
                foodRecipesViewModel = foodRecipesViewModel,
                onBackClick = onBackClick
            )
        }
    }
}