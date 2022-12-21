package io.dev.android.game.ui.adapter

import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import coil.load

object CommonBindingAdapter {

    @BindingAdapter("android:loadImageByResId")
    @JvmStatic
    fun loadImageByResId(
        appCompatImageView: AppCompatImageView,
        @DrawableRes resId: Int
    ) {
        appCompatImageView.load(resId) {
            crossfade(600)
        }
    }

    @BindingAdapter("android:loadBackgroundByResId")
    @JvmStatic
    fun loadBackgroundByResId(
        view: View,
        @DrawableRes resId: Int
    ) {
        view.background = ResourcesCompat.getDrawable(view.resources, resId, view.resources.newTheme())
    }
}