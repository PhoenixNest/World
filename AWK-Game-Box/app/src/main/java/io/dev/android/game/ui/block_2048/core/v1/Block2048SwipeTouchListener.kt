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

private const val SWIPE_THRESHOLD: Int = 50
private const val SWIPE_VELOCITY_THRESHOLD: Int = 50

class Block2048GestureListener(
    private val mapView: Block2048GridView,
    private val moveFunction: (score: Int) -> Unit
) : GestureDetector.SimpleOnGestureListener() {

    private fun onSwipeLeft() {
        val score = mapView.move(ACTION_LEFT)
        moveFunction.invoke(score)
    }

    private fun onSwipeRight() {
        val score = mapView.move(ACTION_RIGHT)
        moveFunction.invoke(score)
    }

    private fun onSwipeUp() {
        val score = mapView.move(ACTION_UP)
        moveFunction.invoke(score)
    }

    private fun onSwipeDown() {
        val score = mapView.move(ACTION_DOWN)
        moveFunction.invoke(score)
    }

    override fun onDown(e: MotionEvent): Boolean {
        return true
    }

    override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
        try {
            val diffY = e2.y - e1.y
            val diffX = e2.x - e1.x
            if (abs(diffX) > abs(diffY)) {
                if (abs(diffX) > SWIPE_THRESHOLD && abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        onSwipeRight()
                    } else {
                        onSwipeLeft()
                    }
                }
            } else {
                if (abs(diffY) > SWIPE_THRESHOLD && abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY < 0) {
                        onSwipeUp()
                    } else {
                        onSwipeDown()
                    }
                }
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }

        return false
    }
}

open class Block2048SwipeTouchListener(
    context: Context,
    mapView: Block2048GridView,
    moveCallback: (score: Int) -> Unit = {}
) : OnTouchListener {

    private val gestureDetector: GestureDetector =
        GestureDetector(context, Block2048GestureListener(mapView, moveCallback))

    companion object {
        private const val TAG = "Block2048SwipeTouchListener"
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(view: View, event: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(event)
    }
}