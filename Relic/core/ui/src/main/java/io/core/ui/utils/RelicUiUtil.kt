package io.core.ui.utils

import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object RelicUiUtil {

    fun View.toggleUiVisibility(isShow: Boolean): View {
        return this.apply {
            visibility = if (isShow) View.VISIBLE else View.INVISIBLE
        }
    }

    fun View.toggleUiGone(isGone: Boolean): View {
        return this.apply {
            visibility = if (isGone) View.GONE else View.VISIBLE
        }
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