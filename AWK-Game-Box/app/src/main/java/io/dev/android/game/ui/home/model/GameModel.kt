package io.dev.android.game.ui.home.model

import androidx.annotation.DrawableRes

data class GameModel(
    val title: String,
    @DrawableRes val iconResId: Int,
    @DrawableRes val backgroundResId: Int,
)