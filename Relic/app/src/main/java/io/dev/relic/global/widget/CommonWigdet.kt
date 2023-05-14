package io.dev.relic.global.widget

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Text {
    data class StringResText(@StringRes val resId: Int) : Text()
}

sealed class Icon {
    data class ImageVectorIcon(val imageVector: ImageVector) : Icon()
    data class DrawableResIcon(@DrawableRes val resId: Int) : Icon()
}
