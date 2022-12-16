package io.dev.android.game.ui.block_2048.core.v1

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import io.dev.android.game.R
import io.dev.android.game.util.DensityUtil.dp

class Block2048GameCube : AppCompatTextView {

    /**
     * Indicate current cube value
     * */
    var innerValue: Int = 0

    /**
     * Indicate the position of current cube
     * */
    var currentPosition: Pair<Int, Int> = Pair(0, 0)

    /**
     * Indicate the position of next cube
     * */
    var nextPosition: Pair<Int, Int> = Pair(0, 0)

    /**
     * Indicate the merged status of current cube
     * */
    var isMerged: Boolean = false

    /**
     * Indicate move action
     * */
    var isMoved: Boolean = false

    companion object {
        private const val TAG = "Block2048GameCube"
        private const val FONT_PATH = "font/alata.ttf"
    }

    init {
        // Modify the style of textView
        elevation = 2.dp()
        gravity = Gravity.CENTER
        typeface = Typeface.createFromAsset(resources.assets, FONT_PATH)
        setTextColor(resources.getColor(R.color.white, resources.newTheme()))
    }

    /* ======================== Constructor ======================== */

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    /* ======================== Logical ======================== */

    fun getValue(): Int {
        return innerValue
    }

    fun setValue(value: Int) {
        innerValue = value
        text = innerValue.toString()

        // Auto-change textSize by current value
        textSize = when {
            (value / 1000 >= 1) -> 21F
            (value / 100 >= 1) -> 26F
            else -> 30F
        }

        background = when (value) {
            2 -> ResourcesCompat.getDrawable(resources, R.drawable.cube2, resources.newTheme())
            4 -> ResourcesCompat.getDrawable(resources, R.drawable.cube4, resources.newTheme())
            8 -> ResourcesCompat.getDrawable(resources, R.drawable.cube8, resources.newTheme())
            16 -> ResourcesCompat.getDrawable(resources, R.drawable.cube16, resources.newTheme())
            32 -> ResourcesCompat.getDrawable(resources, R.drawable.cube32, resources.newTheme())
            64 -> ResourcesCompat.getDrawable(resources, R.drawable.cube64, resources.newTheme())
            128 -> ResourcesCompat.getDrawable(resources, R.drawable.cube128, resources.newTheme())
            256 -> ResourcesCompat.getDrawable(resources, R.drawable.cube256, resources.newTheme())
            512 -> ResourcesCompat.getDrawable(resources, R.drawable.cube512, resources.newTheme())
            1024 -> ResourcesCompat.getDrawable(resources, R.drawable.cube1024, resources.newTheme())
            2048 -> ResourcesCompat.getDrawable(resources, R.drawable.cube2048, resources.newTheme())
            else -> ResourcesCompat.getDrawable(resources, R.drawable.cube4096, resources.newTheme())
        }
    }

    fun setCurrentPosition(y: Int, x: Int) {
        currentPosition = Pair(y, x)
    }

    fun setNextPosition(y: Int, x: Int) {
        nextPosition = Pair(y, x)
    }

}