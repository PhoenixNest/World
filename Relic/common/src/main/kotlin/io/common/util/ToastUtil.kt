package io.common.util

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import io.common.app.BaseApplication.Companion.getApplicationContext

/**
 * 🍞 Toast
 * */
object ToastUtil {

    private var toast: Toast? = null

    /**
     * Show Toast
     *
     * @param content       Display content on the toast
     * @param isLongToast   Duration time of toast
     * */
    fun showToast(
        content: String,
        isLongToast: Boolean = false
    ) {
        if (toast == null) {
            toast = Toast.makeText(
                /* context = */ getApplicationContext(),
                /* text = */ content,
                /* duration = */ if (isLongToast) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
            )
        } else {
            toast?.apply {
                setText(content)
                duration = if (isLongToast) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
            }
        }
        toast?.show()
    }

    /**
     * Show Toast with using string resource id.
     *
     * @param resId         The resource id of string
     * @param isLongToast   Duration time of the toast
     * */
    fun showToast(
        @StringRes resId: Int,
        isLongToast: Boolean = false
    ) {
        val content = getApplicationContext().getString(resId)

        if (toast == null) {
            toast = Toast.makeText(
                /* context = */ getApplicationContext(),
                /* text = */ content,
                /* duration = */ if (isLongToast) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
            )
        } else {
            toast?.apply {
                setText(content)
                duration = if (isLongToast) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
            }
        }
        toast?.show()
    }

    /* ======================== Ext ======================== */

    /**
     * Show toast with context.
     * */
    fun Context.showToast(
        @StringRes resId: Int,
        isLongToast: Boolean = false
    ) {
        val content = this.resources.getString(resId)
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

    /**
     * Show toast with context.
     * */
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
}