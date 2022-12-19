package io.dev.android.game.ui.block_2048.core.v1

import android.annotation.SuppressLint
import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import io.dev.android.game.ui.block_2048.core.v1.Block2048GridView.Companion.ACTION_DOWN
import io.dev.android.game.ui.block_2048.core.v1.Block2048GridView.Companion.ACTION_LEFT
import io.dev.android.game.ui.block_2048.core.v1.Block2048GridView.Companion.ACTION_RIGHT
import io.dev.android.game.ui.block_2048.core.v1.Block2048GridView.Companion.ACTION_UP
import kotlin.math.abs

open class Block2048SwipeTouchListener(
    context: Context,
    mapView: Block2048GridView,
    moveCallback: (score: Int) -> Unit = {}
) : OnTouchListener {

    private val gestureDetector: GestureDetector = GestureDetector(context, Block2048GestureListener(mapView, moveCallback))

    companion object {
        private const val TAG = "Block2048SwipeTouchListener"
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(view: View, event: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(event)
    }
}