package io.dev.relic.global.util.ext

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun Context.showToast(
    @StringRes resId: Int,
    isLongToast: Boolean = false
) {
    val content: String = this.resources.getString(resId)
    Toast.makeText(
        /* context = */ this,
        /* text = */ content,
        /* duration = */ if (isLongToast) {
            Toast.LENGTH_LONG
        } else {
            Toast.LENGTH_SHORT
        }
    ).show()
}

fun Context.showToast(
    content: String,
    isLongToast: Boolean = false
) {
    Toast.makeText(
        /* context = */ this,
        /* text = */ content,
        /* duration = */ if (isLongToast) {
            Toast.LENGTH_LONG
        } else {
            Toast.LENGTH_SHORT
        }
    ).show()
}