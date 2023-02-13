package io.dev.android.game.ui.one_line_finish.core.v1

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.GridView
import io.dev.android.game.R
import io.dev.android.game.data.db.one_line_finish.model.OneLineFinishRoadModel
import io.dev.android.game.util.DensityUtil.dp2Px
import io.dev.android.game.util.UiUtil
import io.dev.android.game.util.ValueUtil

class OneLineGridView : GridView, View.OnTouchListener {

    private val passedPositionsList: MutableList<Int> = mutableListOf()
    private var roadModel: OneLineFinishRoadModel? = null
    private var firstChildPosition = -1
    private var lastChildPosition = -1
    private var downChildPosition = -1
    private var lastMoveChildPosition = -1
    private var listener: OneLineFinishListener? = null

    companion object {
        private const val TAG = "OneLineFinishGridView"
        private const val TAG_FORBIDDEN_VIEW = "forbidden"
    }

    interface OneLineFinishListener {

        fun initGridRoad(initRows: Int, initColumns: Int)

        fun stopGettingRoad(): Boolean

        fun saveProgress(roadModel: OneLineFinishRoadModel?, passedPosition: List<Int>)

        fun passed(roadModel: OneLineFinishRoadModel?)

        fun setIsHelping(isHelping: Boolean)

        fun isHelping(): Boolean
    }

    /* ======================== Constructor ======================== */

    constructor(context: Context) : super(context) {
        setOnTouchListener(this)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setOnTouchListener(this)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setOnTouchListener(this)
    }

    /* ======================== Logical ======================== */

    override fun onTouch(
        view: View?,
        motionEvent: MotionEvent?
    ): Boolean {
        listener?.run {
            if (isHelping()) {
                refreshMapGrid()
                setIsHelping(false)
            }
        }

        if (roadModel == null) {
            return false
        }

        val roadList: List<Int>? = roadModel?.roadList
        if (roadList != null) {
            if (listener != null && passedPositionsList.size + 1 == roadList.size) {
                val roadString: StringBuilder = StringBuilder()
                for (item in roadList) {
                    roadString.append(item)
                    if (roadList.lastIndexOf(item) != roadList.size - 1) {
                        roadString.append(",")
                    }
                }
                listener?.passed(roadModel)
            }

            motionEvent?.let { event ->
                val curChildPos: Int = pointToPosition(
                    event.x.toInt(),
                    event.y.toInt()
                )

                if (curChildPos >= childCount || curChildPos < 0) {
                    return false
                }

                val childView: View = getChildAt(curChildPos)
                if (childView.tag != null && childView.tag.toString() == TAG_FORBIDDEN_VIEW) {
                    return false
                }

                try {
                    when (event.action) {
                        MotionEvent.ACTION_DOWN -> {
                            // Record the point where the finger has pressed down
                            downChildPosition = curChildPos
                            if (lastChildPosition == downChildPosition
                                || downChildPosition == firstChildPosition
                                || passedPositionsList.contains(downChildPosition)
                            ) {
                                // Step back is performed when the view position of the
                                // last operation is the same as the current position
                                if (lastChildPosition == downChildPosition) {
                                    backWard(childView, downChildPosition)
                                }
                                // When the finger-down point is at the starting position
                                // or the passed point contains the current down point,
                                // fall back to the finger-down point
                                else {
                                    for (i in passedPositionsList.size - 1 downTo 0) {
                                        val curPos: Int = passedPositionsList[i]
                                        // When the finger-down point is at the starting position
                                        if (curPos == downChildPosition) {
                                            break
                                        }
                                        // The passed point contains the current finger-down point
                                        val curChildView: View = getChildAt(curPos)
                                        if (curChildView.tag == null) {
                                            break
                                        }
                                        // Back to the finger-down point
                                        backWard(curChildView, curPos)
                                    }
                                }
                            }
                            // Otherwise try to move forward
                            else {
                                forward(childView, downChildPosition)
                            }
                        }
                        MotionEvent.ACTION_MOVE -> {
                            // If the current position is the start position
                            if (curChildPos == firstChildPosition
                                && lastChildPosition != firstChildPosition
                            ) {
                                // The current position is the start position and recedes when the previous position of the "pen end" is the start position
                                val lastChildView: View = getChildAt(lastChildPosition)
                                if (lastChildView.tag is Int
                                    && lastChildView.tag == firstChildPosition
                                ) {
                                    backWard(lastChildView, lastChildPosition)
                                }
                            }
                            // The state does not change when you swipe back and forth only within a childView
                            else if (curChildPos != lastMoveChildPosition) {
                                if (curChildPos != downChildPosition) {
                                    // When the record is not empty, there are two situations to perform a backward step:
                                    // + direct backward step;
                                    // + sudden backward step when moving forward;
                                    if (childView.tag != null) {
                                        // Direct backward: Performs backward when the last action point equals the current move point
                                        if (lastChildPosition == curChildPos) {
                                            backWard(childView, curChildPos)
                                        }
                                        // When forward with suddenly backward:
                                        // the end of the pen is retreated, and currentChildPosition is the
                                        // position of the previous step of the tail.
                                        else {
                                            val lastChildView: View = getChildAt(lastChildPosition)
                                            if (lastChildView.tag is Int
                                                && (lastChildView.tag as Int) == curChildPos
                                            ) {
                                                backWard(lastChildView, lastChildPosition)
                                            }
                                        }
                                    }
                                    // Try to forward when the tag is null
                                    else {
                                        forward(childView, curChildPos)
                                    }
                                }
                                // Try to forward when the previous point is the finger-down point and the tag is null
                                else if (childView.tag == null) {
                                    forward(childView, curChildPos)
                                }
                            }
                            lastMoveChildPosition = curChildPos
                        }
                    }
                } catch (exception: Exception) {
                    UiUtil.showToast(context, "Error, message: $exception")
                }
            }
        }

        return false
    }

    fun refreshMapGrid() {
        if (roadModel == null) {
            return
        }

        initMapGrid(roadModel, listener)
    }

    fun initMapGrid(
        roadModel: OneLineFinishRoadModel?,
        listener: OneLineFinishListener?
    ) {
        listener?.setIsHelping(false)

        if (roadModel == null) {
            return
        }

        this.listener = listener
        this.roadModel = roadModel
        this.lastChildPosition = roadModel.roadList[0]
        this.firstChildPosition = roadModel.roadList[0]
        this.passedPositionsList.clear()
        listener?.stopGettingRoad()
        setupGridView(roadModel)
    }

    fun initPassedGrid(
        rows: Int,
        columns: Int,
        roadPosition: String,
        passedPosition: String,
        listener: OneLineFinishListener?
    ) {
        listener?.setIsHelping(false)
        val roadPositionArray: Array<String> = roadPosition.split("[,]").toTypedArray()
        val roadList = ValueUtil.getIntListFromString(roadPositionArray)
        if (roadList.isEmpty()) {
            UiUtil.showToast(context, "Restart previous game has failure, start new game...")
            listener?.initGridRoad(rows, columns)
            return
        }

        // Setup parameters
        this.roadModel = OneLineFinishRoadModel(rows = rows, columns = columns, roadList = roadList)
        this.passedPositionsList.clear()
        this.listener = listener
        this.lastChildPosition = roadList[0]
        this.firstChildPosition = roadList[0]

        setupGridView(roadModel)

        if (passedPositionsList.size == 0) {
            if (listener != null) {
                listener.stopGettingRoad()
                return
            }
        }

        post {
            listener?.stopGettingRoad()
            val passPositionArray: Array<String> = passedPosition.split("[,]").toTypedArray()
            for (item: String in passPositionArray) {
                try {
                    val position = item.toInt()
                    val childView: View = getChildAt(position)
                    forward(childView, position)
                } catch (exception: Exception) {
                    listener?.initGridRoad(rows, columns)
                    UiUtil.showToast(context, "Restart previous game has failure, start new game...")
                    return@post
                }
            }
            if (passedPositionsList.size + 1 == roadList.size) {
                listener?.passed(roadModel)
            }
        }
    }

    private fun setupGridView(model: OneLineFinishRoadModel?) {
        if (model != null) {
            val layoutParams = layoutParams
            layoutParams.width = 60F.dp2Px() * model.columns
            layoutParams.height = 60F.dp2Px() * model.columns
            setLayoutParams(layoutParams)

            numColumns = model.columns
            adapter = OneLineGridAdapter().setRoadList(roadModel)
        }
    }

    private fun checkDirection(lastPos: Int, curPos: Int): OneLineDirection? {
        if (lastPos in 0 until childCount) {
            if (lastPos + 1 == curPos && curPos % numColumns != 0) {
                return OneLineDirection.RIGHT
            } else if (lastPos + numColumns == curPos) {
                return OneLineDirection.UP
            } else if (lastPos - numColumns == curPos) {
                return OneLineDirection.DOWN
            } else if (lastPos - 1 == curPos && lastPos % numColumns != 0) {
                return OneLineDirection.LEFT
            }
        }

        return null
    }

    private fun forward(curChildView: View?, curChildPos: Int) {
        if (curChildView == null) {
            return
        }

        post {
            if (lastChildPosition + 1 == curChildPos && curChildPos % numColumns != 0
                || lastChildPosition + numColumns == curChildPos
                || lastChildPosition - numColumns == curChildPos
                || lastChildPosition - 1 == curChildPos && lastChildPosition % numColumns != 0
            ) {
                when (checkDirection(lastChildPosition, curChildPos)) {
                    OneLineDirection.RIGHT -> {
                        val lastChildView: View = getChildAt(lastChildPosition)
                        val curMainView: View = curChildView.findViewById(R.id.view_main)
                        val lastRight: View = lastChildView.findViewById(R.id.view_right)
                        val curLeft: View = curChildView.findViewById(R.id.view_left)
                        lastRight.setBackgroundColor(resources.getColor(R.color.color_one_line_selected, resources.newTheme()))
                        curLeft.setBackgroundColor(resources.getColor(R.color.color_one_line_selected, resources.newTheme()))
                        curMainView.setBackgroundResource(R.drawable.one_line_grid_selected)
                    }
                    OneLineDirection.UP -> {
                        val lastChildView: View = getChildAt(lastChildPosition)
                        val curMainView: View = curChildView.findViewById(R.id.view_main)
                        val lastBottom: View = lastChildView.findViewById(R.id.view_bottom)
                        val curTop: View = curChildView.findViewById(R.id.view_top)
                        lastBottom.setBackgroundColor(resources.getColor(R.color.color_one_line_selected, resources.newTheme()))
                        curTop.setBackgroundColor(resources.getColor(R.color.color_one_line_selected, resources.newTheme()))
                        curMainView.setBackgroundResource(R.drawable.one_line_grid_selected)
                    }
                    OneLineDirection.DOWN -> {
                        val lastChildView: View = getChildAt(lastChildPosition)
                        val curMainView: View = curChildView.findViewById(R.id.view_main)
                        val lastTop: View = lastChildView.findViewById(R.id.view_top)
                        val curBottom: View = curChildView.findViewById(R.id.view_bottom)
                        lastTop.setBackgroundColor(resources.getColor(R.color.color_one_line_selected, resources.newTheme()))
                        curBottom.setBackgroundColor(resources.getColor(R.color.color_one_line_selected, resources.newTheme()))
                        curMainView.setBackgroundResource(R.drawable.one_line_grid_selected)
                    }
                    OneLineDirection.LEFT -> {
                        val lastChildView: View = getChildAt(lastChildPosition)
                        val curMainView: View = curChildView.findViewById(R.id.view_main)
                        val lastLeft: View = lastChildView.findViewById(R.id.view_left)
                        val curRight: View = curChildView.findViewById(R.id.view_right)
                        lastLeft.setBackgroundColor(resources.getColor(R.color.color_one_line_selected, resources.newTheme()))
                        curRight.setBackgroundColor(resources.getColor(R.color.color_one_line_selected, resources.newTheme()))
                        curMainView.setBackgroundResource(R.drawable.one_line_grid_selected)
                    }
                    else -> {}
                }
            }

            passedPositionsList.add(curChildPos)
            listener?.saveProgress(roadModel, passedPositionsList)
            curChildView.tag = lastChildPosition
            lastChildPosition = curChildPos
        }
    }

    private fun backWard(curChildView: View?, curChildPos: Int) {
        if (curChildView == null) {
            return
        }

        post {
            if (curChildView.tag !is Int) {
                return@post
            }

            if (lastChildPosition in 0 until childCount) {
                lastChildPosition = (curChildView.tag as Int)
                val lastChildView: View = getChildAt(lastChildPosition)
                when (checkDirection(lastChildPosition, curChildPos)) {
                    OneLineDirection.RIGHT -> {
                        val lastRight: View = lastChildView.findViewById(R.id.view_right)
                        lastRight.setBackgroundColor(resources.getColor(R.color.color_transparency, resources.newTheme()))
                    }
                    OneLineDirection.UP -> {
                        val lastBottom: View = lastChildView.findViewById(R.id.view_bottom)
                        lastBottom.setBackgroundColor(resources.getColor(R.color.color_transparency, resources.newTheme()))
                    }
                    OneLineDirection.DOWN -> {
                        val lastTop: View = lastChildView.findViewById(R.id.view_top)
                        lastTop.setBackgroundColor(resources.getColor(R.color.color_transparency, resources.newTheme()))
                    }
                    OneLineDirection.LEFT -> {
                        val lastLeft: View = lastChildView.findViewById(R.id.view_left)
                        lastLeft.setBackgroundColor(resources.getColor(R.color.color_transparency, resources.newTheme()))
                    }
                    else -> {}
                }
                passedPositionsList.removeAt(passedPositionsList.size - 1)
                listener?.saveProgress(roadModel, passedPositionsList)

                curChildView.apply {
                    tag = null
                    findViewById<View>(R.id.view_right).setBackgroundColor(resources.getColor(R.color.color_transparency, resources.newTheme()))
                    findViewById<View>(R.id.view_bottom).setBackgroundColor(resources.getColor(R.color.color_transparency, resources.newTheme()))
                    findViewById<View>(R.id.view_top).setBackgroundColor(resources.getColor(R.color.color_transparency, resources.newTheme()))
                    findViewById<View>(R.id.view_left).setBackgroundColor(resources.getColor(R.color.color_transparency, resources.newTheme()))
                    findViewById<View>(R.id.view_main).setBackgroundResource(R.drawable.one_line_grid_unselected)
                }
            }
        }
    }

    fun getHelp() {
        refreshMapGrid()
        post {
            if (roadModel == null) {
                return@post
            }

            listener?.setIsHelping(true)
            roadModel?.run {
                var lastChildPos = roadList[0]
                for (i in 1 until roadList.size) {
                    val curChildPos = roadList[i]
                    val curChildView: View = getChildAt(curChildPos)
                    val lastChildView: View = getChildAt(lastChildPos)
                    val curMainView: View = curChildView.findViewById(R.id.view_main)
                    when (checkDirection(lastChildPos, curChildPos)) {
                        OneLineDirection.RIGHT -> {
                            val lastRight: View = lastChildView.findViewById(R.id.view_right)
                            val curLeft: View = curChildView.findViewById(R.id.view_left)
                            lastRight.setBackgroundColor(resources.getColor(R.color.color_one_line_help_selected, resources.newTheme()))
                            curLeft.setBackgroundColor(resources.getColor(R.color.color_one_line_help_selected, resources.newTheme()))
                            curMainView.setBackgroundResource(R.drawable.one_line_grid_help_selected)
                        }
                        OneLineDirection.DOWN -> {
                            val lastBottom: View = lastChildView.findViewById(R.id.view_bottom)
                            val curTop: View = curChildView.findViewById(R.id.view_top)
                            lastBottom.setBackgroundColor(resources.getColor(R.color.color_one_line_help_selected, resources.newTheme()))
                            curTop.setBackgroundColor(resources.getColor(R.color.color_one_line_help_selected, resources.newTheme()))
                            curMainView.setBackgroundResource(R.drawable.one_line_grid_help_selected)
                        }
                        OneLineDirection.UP -> {
                            val lastTop: View = lastChildView.findViewById(R.id.view_top)
                            val curBottom: View = curChildView.findViewById(R.id.view_bottom)
                            lastTop.setBackgroundColor(resources.getColor(R.color.color_one_line_help_selected, resources.newTheme()))
                            curBottom.setBackgroundColor(resources.getColor(R.color.color_one_line_help_selected, resources.newTheme()))
                            curMainView.setBackgroundResource(R.drawable.one_line_grid_help_selected)
                        }
                        OneLineDirection.LEFT -> {
                            val lastLeft: View = lastChildView.findViewById(R.id.view_left)
                            val curRight: View = curChildView.findViewById(R.id.view_right)
                            lastLeft.setBackgroundColor(resources.getColor(R.color.color_one_line_help_selected, resources.newTheme()))
                            curRight.setBackgroundColor(resources.getColor(R.color.color_one_line_help_selected, resources.newTheme()))
                            curMainView.setBackgroundResource(R.drawable.one_line_grid_help_selected)
                        }
                        else -> {}
                    }
                    lastChildPos = curChildPos
                }
            }
        }
    }
}