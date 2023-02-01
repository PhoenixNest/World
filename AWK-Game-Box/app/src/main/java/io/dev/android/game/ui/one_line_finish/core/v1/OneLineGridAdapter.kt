package io.dev.android.game.ui.one_line_finish.core.v1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import io.dev.android.game.R
import io.dev.android.game.data.db.one_line_finish.model.OneLineFinishRoadModel

class OneLineGridAdapter : BaseAdapter() {

    private var size: Int = 0
    private var startPosition: Int = 0
    private var roadList: List<Int> = emptyList()

    companion object {
        private const val TAG = "OneLineFinishGridAdapter"
        private const val TAG_FORBIDDEN_VIEW = "forbidden"
    }

    fun setRoadList(roadModel: OneLineFinishRoadModel?): OneLineGridAdapter {
        if (roadModel != null) {
            this.roadList = roadModel.roadList
            if (roadList.isEmpty()) {
                this.size = 0
                this.startPosition = 0
            } else {
                this.size = roadModel.rows * roadModel.columns
                this.startPosition = roadList[0]
            }
        }

        return this
    }

    override fun getCount(): Int {
        return roadList.size
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup?
    ): View? {
        var view: View? = convertView
        if (view == null && parent != null) {
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_one_line_finish, parent, false)
        }

        if (startPosition == position) {
            view?.findViewById<View>(R.id.view_main)?.setBackgroundResource(R.drawable.one_line_grid_start_point)
        }

        var isAllowed = false
        for (item in roadList) {
            if (item == position) {
                isAllowed = true
            }
        }

        if (!isAllowed) {
            view?.tag = TAG_FORBIDDEN_VIEW
            view?.findViewById<View>(R.id.view_main)?.setBackgroundResource(R.drawable.one_line_grid_obstacle)
        }

        return view
    }
}