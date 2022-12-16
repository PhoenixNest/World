package io.dev.android.game.ui.block_2048.core.v1

import android.view.GestureDetector
import android.view.MotionEvent
import kotlin.math.abs

class Block2048GestureListener(
    private val gameMapView: Block2048GridView,
    private val moveCallback: (score: Int) -> Unit
) : GestureDetector.SimpleOnGestureListener() {

    companion object {
        private const val SWIPE_THRESHOLD: Int = 50
        private const val SWIPE_VELOCITY_THRESHOLD: Int = 50
    }

    private fun onSwipeLeft() {
        val score = gameMapView.move(Block2048GridView.ACTION_LEFT)
        moveCallback.invoke(score)
    }

    private fun onSwipeRight() {
        val score = gameMapView.move(Block2048GridView.ACTION_RIGHT)
        moveCallback.invoke(score)
    }

    private fun onSwipeUp() {
        val score = gameMapView.move(Block2048GridView.ACTION_UP)
        moveCallback.invoke(score)
    }

    private fun onSwipeDown() {
        val score = gameMapView.move(Block2048GridView.ACTION_DOWN)
        moveCallback.invoke(score)
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