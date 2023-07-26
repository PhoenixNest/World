package io.dev.relic.feature.common.setting

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import com.google.accompanist.navigation.animation.composable
import io.dev.relic.feature.route.MainFeatureRoute.SettingUnit.graphSetting
import io.dev.relic.feature.route.MainFeatureRoute.SettingUnit.routeSettingScreen
import io.dev.relic.global.RelicConstants.GlobalLogTags.GLOBAL_TAG_NAV_CONTROLLER
import io.dev.relic.global.utils.LogUtil

fun NavController.navigateToSettingScreen(navOptions: NavOptions? = null) {
    LogUtil.debug(GLOBAL_TAG_NAV_CONTROLLER, "[Navigate destination]: SettingScreen")
    this.navigate(
        route = routeSettingScreen,
        navOptions = navOptions
    )
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.navSettingScreen(
    onBackClick: () -> Unit
) {
    navigation(
        startDestination = routeSettingScreen,
        route = graphSetting
    ) {
        composable(route = routeSettingScreen) {
            SettingScreenRoute(
                onBackClick = onBackClick
            )
        }
    }
}