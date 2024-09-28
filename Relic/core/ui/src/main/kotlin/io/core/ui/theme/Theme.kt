package io.core.ui.theme

import androidx.compose.runtime.Composable
import io.common.util.TimeUtil
import io.common.util.TimeUtil.Season.AUTUMN
import io.common.util.TimeUtil.Season.SPRING
import io.common.util.TimeUtil.Season.SUMMER
import io.common.util.TimeUtil.Season.WINTER
import io.core.ui.theme.autumn.AutumnTheme
import io.core.ui.theme.spring.SpringTheme
import io.core.ui.theme.summer.SummerTheme
import io.core.ui.theme.winter.WinterTheme

@Composable
fun RelicAppTheme(content: @Composable () -> Unit) {
    when (TimeUtil.getCurrentSeason()) {
        SPRING -> SpringTheme.AppTheme(content = content)
        SUMMER -> SummerTheme.AppTheme(content = content)
        AUTUMN -> AutumnTheme.AppTheme(content = content)
        WINTER -> WinterTheme.AppTheme(content = content)
    }
}