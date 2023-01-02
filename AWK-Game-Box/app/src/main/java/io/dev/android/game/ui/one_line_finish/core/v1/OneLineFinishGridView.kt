package io.dev.android.game.ui.one_line_finish.core.v1

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.GridView
import io.dev.android.game.data.db.one_line_finish.model.OneLineFinishRoadModel
import io.dev.android.game.util.DensityUtil.dp2Px

class OneLineFinishGridView : GridView, View.OnTouchListener {

    private val passedPositionsList: List<Int> = emptyList()
    private var roadModel: OneLineFinishRoadModel? = null
    private var firstChildPosition = -1
    private var lastChildPosition = -1
    private val downChildPosition = -1
    private val lastMveChildPosition = -1
    private var listener: OneLineFinishListener? = null

    interface OneLineFinishListener {

        fun initGridRoad(initRows: Int, initColumns: Int)

        fun stopGettingRoad(): Boolean

        fun saveProgress(roadModel: OneLineFinishRoadModel, passedPosition: List<Int>)

        fun passed(roadModel: OneLineFinishRoadModel)

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

        this.roadModel = roadModel
        this.listener = listener
        this.lastChildPosition = roadModel.roadList[0]
        this.firstChildPosition = roadModel.roadList[0]
        listener?.stopGettingRoad()
        setupGridView(roadModel)
    }

    fun initPassedGrid(
        rows: Int,
        columns: Int,
        roadPosition: String,
        listener: OneLineFinishListener?
    ) {
        listener?.setIsHelping(false)
        // roadPosition.split(regex = "")
    }

    private fun setupGridView(model: OneLineFinishRoadModel) {
        numColumns = model.columns
        layoutParams.apply {
            width = 60F.dp2Px() * model.columns
        }
    }
}