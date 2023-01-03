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

class OneLineFinishGridView : GridView, View.OnTouchListener {

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

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    /* ======================== Logical ======================== */

    init {
        setOnTouchListener(this)
    }

    override fun onTouch(
        view: View?,
        event: MotionEvent?
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

        val roadList = roadModel?.roadList
        if (roadList != null) {
            if (listener != null && passedPositionsList.size + 1 == roadList.size) {
                val roadString = StringBuilder()
                for (item in roadList) {
                    roadString.append(item)
                    if (roadList.lastIndexOf(item) != roadList.size - 1) {
                        roadString.append(",")
                    }
                }
                listener?.passed(roadModel)
            }

            event?.run {
                val curX: Float = x
                val curY: Float = y
                val curChildPos = pointToPosition(curX.toInt(), curY.toInt())
                if (curChildPos >= childCount || curChildPos < 0) {
                    return false
                }

                val childView: View = getChildAt(curChildPos)
                if (childView.tag != null && childView.tag.toString() == TAG_FORBIDDEN_VIEW) {
                    return false
                }

                try {
                    when (action) {
                        MotionEvent.ACTION_DOWN -> {
                            downChildPosition = curChildPos
                            if (lastChildPosition == downChildPosition
                                || downChildPosition == firstChildPosition
                                || passedPositionsList.contains(downChildPosition)
                            ) {
                                if (lastChildPosition == downChildPosition) {
                                    backWard(childView, downChildPosition)
                                } else {
                                    for (i in passedPositionsList.size - 1 downTo 0) {
                                        val curPos = passedPositionsList[i]
                                        if (curPos == downChildPosition) {
                                            break
                                        }

                                        val curChildView: View = getChildAt(curPos)
                                        if (curChildView.tag == null) {
                                            break
                                        }

                                        backWard(curChildView, curPos)
                                    }
                                }
                            } else {
                                forward(childView, downChildPosition)
                            }
                        }
                        MotionEvent.ACTION_MOVE -> {
                            if (curChildPos == firstChildPosition
                                && lastChildPosition != firstChildPosition
                            ) {
                                val lastChildView: View = getChildAt(lastChildPosition)
                                if (lastChildView.tag is Int
                                    && (lastChildView.tag as Int) == firstChildPosition
                                ) {
                                    backWard(lastChildView, lastChildPosition)
                                }
                            }
                            if (curChildPos != lastMoveChildPosition) {
                                if (curChildPos != downChildPosition) {
                                    if (childView.tag != null) {
                                        if (lastChildPosition == curChildPos) {
                                            backWard(childView, curChildPos)
                                        } else {
                                            val lastChildView: View = getChildAt(lastChildPosition)
                                            if (lastChildView.tag is Int
                                                && (lastChildView.tag as Int) == curChildPos
                                            ) {
                                                backWard(lastChildView, lastChildPosition)
                                            }
                                        }
                                    } else {
                                        forward(childView, curChildPos)
                                    }
                                } else if (childView.tag == null) {
                                    forward(childView, curChildPos)
                                }
                            }
                            lastMoveChildPosition = curChildPos
                        }
                    }
                } catch (exception: Exception) {
                    UiUtil.showToast(context, "Error, message: $exception")
                }
                return false
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
        adapter = OneLineFinishGridAdapter().setRoadList(roadModel)

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
            numColumns = model.columns
            layoutParams.apply {
                width = 60F.dp2Px() * model.columns
            }
        }
    }

    private fun checkDirection(lastPos: Int, curPos: Int): OneLineFinishDirection? {
        if (lastPos + 1 == curPos && curPos % numColumns != 0
            || lastPos + numColumns == curPos
            || lastPos - numColumns == curPos
            || lastPos - 1 == curPos && lastPos % numColumns != 0
        ) {
            if (lastPos in 0 until childCount) {
                return OneLineFinishDirection.RIGHT
            } else if (lastPos + numColumns == curPos) {
                return OneLineFinishDirection.BOTTOM
            } else if (lastPos - numColumns == curPos) {
                return OneLineFinishDirection.TOP
            } else if (lastPos - 1 == curPos && lastPos % numColumns != 0) {
                return OneLineFinishDirection.LEFT
            }
        }

        return null
    }

    private fun forward(curChildView: View?, curChildPos: Int) {
        if (curChildView == null) {
            return
        }

        post {
            val lastChildView: View = getChildAt(lastChildPosition)
            val curMain: View = curChildView.findViewById(R.id.view_main)
            when (checkDirection(lastChildPosition, curChildPos)) {
                OneLineFinishDirection.RIGHT -> {
                    val lastRight: View = lastChildView.findViewById(R.id.view_right)
                    val curLeft: View = curChildView.findViewById(R.id.view_left)
                    lastRight.setBackgroundResource(R.color.cube8)
                    curLeft.setBackgroundResource(R.color.cube8)
                    curMain.setBackgroundResource(R.drawable.grid_selected)
                }
                OneLineFinishDirection.BOTTOM -> {
                    val lastBottom: View = lastChildView.findViewById(R.id.view_bottom)
                    val curTop: View = curChildView.findViewById(R.id.view_top)
                    lastBottom.setBackgroundResource(R.color.cube8)
                    curTop.setBackgroundResource(R.color.cube8)
                    curMain.setBackgroundResource(R.drawable.grid_selected)
                }
                OneLineFinishDirection.TOP -> {
                    val lastTop: View = lastChildView.findViewById(R.id.view_top)
                    val curBottom: View = curChildView.findViewById(R.id.view_bottom)
                    lastTop.setBackgroundResource(R.color.cube8)
                    curBottom.setBackgroundResource(R.color.cube8)
                    curMain.setBackgroundResource(R.drawable.grid_selected)
                }
                OneLineFinishDirection.LEFT -> {
                    val lastLeft: View = lastChildView.findViewById(R.id.view_left)
                    val curRight: View = curChildView.findViewById(R.id.view_right)
                    lastLeft.setBackgroundResource(R.color.cube8)
                    curRight.setBackgroundResource(R.color.cube8)
                    curMain.setBackgroundResource(R.drawable.grid_selected)
                }
                else -> {}
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
                    OneLineFinishDirection.RIGHT -> {
                        val lastRight: View = lastChildView.findViewById(R.id.view_right)
                        lastRight.setBackgroundResource(R.color.color_transparency)
                    }
                    OneLineFinishDirection.BOTTOM -> {
                        val lastBottom: View = lastChildView.findViewById(R.id.view_bottom)
                        lastBottom.setBackgroundResource(R.color.color_transparency)
                    }
                    OneLineFinishDirection.TOP -> {
                        val lastTop: View = lastChildView.findViewById(R.id.view_top)
                        lastTop.setBackgroundResource(R.color.color_transparency)
                    }
                    OneLineFinishDirection.LEFT -> {
                        val lastLeft: View = lastChildView.findViewById(R.id.view_left)
                        lastLeft.setBackgroundResource(R.color.color_transparency)
                    }
                    else -> {}
                }
                passedPositionsList.removeAt(passedPositionsList.size - 1)
                listener?.saveProgress(roadModel, passedPositionsList)

                curChildView.apply {
                    tag = null
                    findViewById<View>(R.id.view_right).setBackgroundResource(R.color.color_transparency)
                    findViewById<View>(R.id.view_bottom).setBackgroundResource(R.color.color_transparency)
                    findViewById<View>(R.id.view_top).setBackgroundResource(R.color.color_transparency)
                    findViewById<View>(R.id.view_left).setBackgroundResource(R.color.color_transparency)
                    findViewById<View>(R.id.view_main).setBackgroundResource(R.drawable.grid_unselected)
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

                    val curMain: View = curChildView.findViewById(R.id.view_main)
                    when (checkDirection(lastChildPos, curChildPos)) {
                        OneLineFinishDirection.RIGHT -> {
                            val lastRight: View = lastChildView.findViewById(R.id.view_right)
                            val curLeft: View = curChildView.findViewById(R.id.view_left)
                            lastRight.setBackgroundResource(R.color.cubeSuper)
                            curLeft.setBackgroundResource(R.color.cubeSuper)
                            curMain.setBackgroundResource(R.drawable.grid_help_selected)
                        }
                        OneLineFinishDirection.BOTTOM -> {
                            val lastBottom: View = lastChildView.findViewById(R.id.view_bottom)
                            val curTop: View = curChildView.findViewById(R.id.view_top)
                            lastBottom.setBackgroundResource(R.color.cubeSuper)
                            curTop.setBackgroundResource(R.color.cubeSuper)
                            curMain.setBackgroundResource(R.drawable.grid_help_selected)
                        }
                        OneLineFinishDirection.TOP -> {
                            val lastTop: View = lastChildView.findViewById(R.id.view_top)
                            val curBottom: View = curChildView.findViewById(R.id.view_bottom)
                            lastTop.setBackgroundResource(R.color.cubeSuper)
                            curBottom.setBackgroundResource(R.color.cubeSuper)
                            curMain.setBackgroundResource(R.drawable.grid_help_selected)
                        }
                        OneLineFinishDirection.LEFT -> {
                            val lastLeft: View = lastChildView.findViewById(R.id.view_left)
                            val curRight: View = curChildView.findViewById(R.id.view_right)
                            lastLeft.setBackgroundResource(R.color.cubeSuper)
                            curRight.setBackgroundResource(R.color.cubeSuper)
                            curMain.setBackgroundResource(R.drawable.grid_help_selected)
                        }
                        else -> {}
                    }
                    lastChildPos = curChildPos
                }
            }
        }
    }
}