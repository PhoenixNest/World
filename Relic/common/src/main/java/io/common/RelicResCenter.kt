package io.common

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.content.res.AppCompatResources
import io.common.app.BaseApplication.Companion.getApplicationContext

object RelicResCenter {

    fun getString(@StringRes stringResId: Int): String {
        val context: Context = getApplicationContext()
        return context.getString(stringResId)
    }

    fun getDrawable(@DrawableRes drawableResId: Int): Drawable? {
        val context: Context = getApplicationContext()
        return AppCompatResources.getDrawable(context, drawableResId)
    }

    fun getColor(@ColorRes colorResId: Int): Int {
        val context: Context = getApplicationContext()
        return context.getColor(colorResId)
    }

}