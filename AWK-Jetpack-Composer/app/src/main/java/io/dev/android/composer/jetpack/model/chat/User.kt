package io.dev.android.composer.jetpack.model.chat

import androidx.annotation.DrawableRes
import io.dev.android.composer.jetpack.R

data class User(
    val id: Int,
    val userName: String,
    @DrawableRes val userIcon: Int
) {
    companion object {
        val admin = User(0, "Admin", R.drawable.ic_android)
    }
}
