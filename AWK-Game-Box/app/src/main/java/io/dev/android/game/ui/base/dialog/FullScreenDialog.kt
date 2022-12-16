package io.dev.android.game.ui.base.dialog

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window

open class FullScreenDialog : BaseDialog() {

    companion object {
        private const val TAG = "FullScreenDialog"
        private const val IDENTIFIER_TITLE_DIVIDER = "android:id/titleDivider"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, android.R.style.Theme_Translucent_NoTitleBar)
    }

    @SuppressLint("DiscouragedApi")
    override fun onStart() {
        super.onStart()
        if (showsDialog) {
            dialog?.window?.let { window: Window ->
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            }

            // Adaptation for Android 4.3
            val dividerId: Int = resources.getIdentifier(IDENTIFIER_TITLE_DIVIDER, null, null)
            dialog?.findViewById<View?>(dividerId)?.setBackgroundColor(Color.TRANSPARENT)
        }
    }

}