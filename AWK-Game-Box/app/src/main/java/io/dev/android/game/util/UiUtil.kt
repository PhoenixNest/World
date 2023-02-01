package io.dev.android.game.util

import android.content.Context
import android.widget.Toast

object UiUtil {

    private var toast: Toast? = null

    fun showToast(context: Context, content: String) {
        if (toast != null) {
            return
        }

        toast = Toast(context).apply {
            setText(content)
            duration = Toast.LENGTH_SHORT
            show()
        }
    }
}