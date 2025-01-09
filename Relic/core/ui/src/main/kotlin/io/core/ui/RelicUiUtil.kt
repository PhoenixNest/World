package io.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object RelicUiUtil {


    /**
     * The default light scrim, as defined by androidx and the platform:
     * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:activity/activity/src/main/java/androidx/activity/EdgeToEdge.kt;l=35-38;drc=27e7d52e8604a080133e8b842db10c89b4482598
     */
    val lightScrim = android.graphics.Color.argb(
        /* alpha = */ 0xe6,
        /* red = */ 0xFF,
        /* green = */ 0xFF,
        /* blue = */ 0xFF
    )

    /**
     * The default dark scrim, as defined by androidx and the platform:
     * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:activity/activity/src/main/java/androidx/activity/EdgeToEdge.kt;l=40-44;drc=27e7d52e8604a080133e8b842db10c89b4482598
     */
    val darkScrim = android.graphics.Color.argb(
        /* alpha = */ 0x80,
        /* red = */ 0x1b,
        /* green = */ 0x1b,
        /* blue = */ 0x1b
    )

    /**
     * @see androidx.compose.material3.tokens.NavigationBarTokens.ContainerHeight
     * */
    val DEFAULT_BOTTOM_NAVIGATION_BAR_HEIGHT = 80.dp

    /**
     * @see androidx.compose.material3.tokens.NavigationRailTokens.ContainerWidth
     * */
    val DEFAULT_RAIL_BAR_WIDTH = 80.dp

    /**
     * @see androidx.compose.material3.DragHandleVerticalPadding
     * */
    val DEFAULT_SHEET_DRAG_HANDLE_HEIGHT = 44.dp

    fun convertPixelToDp(
        density: Float,
        pixel: Int
    ): Dp {
        return (pixel / density).dp
    }

    @Composable
    fun getCurrentScreenWidthDp(): Dp {
        return LocalConfiguration.current.screenWidthDp.dp
    }

    @Composable
    fun getCurrentScreenHeightDp(): Dp {
        return LocalConfiguration.current.screenHeightDp.dp
    }
}