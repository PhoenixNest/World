package io.core.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object RelicUiUtil {

    @Composable
    fun getCurrentScreenWidthDp(): Dp {
        return LocalConfiguration.current.screenWidthDp.dp
    }

    @Composable
    fun getCurrentScreenHeightDp(): Dp {
        return LocalConfiguration.current.screenHeightDp.dp
    }

}