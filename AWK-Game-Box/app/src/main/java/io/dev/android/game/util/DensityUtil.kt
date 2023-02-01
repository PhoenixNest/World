package io.dev.android.game.util

import android.content.res.Resources
import android.util.TypedValue
import io.dev.android.game.MyApplication
import kotlin.math.roundToInt

object DensityUtil {

    fun Float.dp(): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this, MyApplication.getInstance().resources.displayMetrics
        )
    }

    fun Int.dp(): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(), MyApplication.getInstance().resources.displayMetrics
        )
    }

    fun Int.dpInt(): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(), MyApplication.getInstance().resources.displayMetrics
        ).roundToInt()
    }

    fun Float.dp2Px(): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (this * scale + 0.5F).toInt()
    }
}