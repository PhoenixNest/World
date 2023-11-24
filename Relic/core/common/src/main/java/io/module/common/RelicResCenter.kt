package io.module.common

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.content.res.AppCompatResources
import io.module.common.app.BaseApplication

object RelicResCenter {

    fun getString(@StringRes stringResId: Int): String {
        val context: Context = BaseApplication.getApplicationContext()
        return context.getString(stringResId)
    }

    fun getDrawable(@DrawableRes drawableResId: Int): Drawable? {
        val context: Context = BaseApplication.getApplicationContext()
        return AppCompatResources.getDrawable(context, drawableResId)
    }

    fun getColor(@ColorRes colorResId: Int): Int {
        val context: Context = BaseApplication.getApplicationContext()
        return context.getColor(colorResId)
    }

}