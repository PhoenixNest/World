package io.common.ext

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper

object ContextExt {

    fun Context.findActivity(): Activity {
        var context = this
        while (context is ContextWrapper) {
            if (context is Activity) {
                return context
            }

            context = context.baseContext
        }

        throw IllegalArgumentException("No Activity")
    }
}