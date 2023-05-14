package io.dev.relic.core.route

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Hive
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Hive
import androidx.compose.material.icons.outlined.Home
import io.dev.relic.R
import io.dev.relic.global.widget.Icon
import io.dev.relic.global.widget.Icon.ImageVectorIcon

enum class RelicTopLevelDestination(
    @StringRes val titleResId: Int,
    @StringRes val labelResId: Int = titleResId,
    val selectedIcon: Icon,
    val unselectedIcon: Icon
) {
    Home(
        titleResId = R.string.home_title,
        labelResId = R.string.home_title,
        selectedIcon = ImageVectorIcon(imageVector = Icons.Filled.Home),
        unselectedIcon = ImageVectorIcon(imageVector = Icons.Outlined.Home),
    ),
    Hub(
        titleResId = R.string.hub_title,
        labelResId = R.string.hub_title,
        selectedIcon = ImageVectorIcon(imageVector = Icons.Filled.Hive),
        unselectedIcon = ImageVectorIcon(imageVector = Icons.Outlined.Hive),
    )
}