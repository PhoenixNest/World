package io.dev.android.game.data.db.one_line_finish.model

data class OneLineFinishRoadModel(
    val rows: Int,
    val columns: Int,
    val roadList: List<Int>
) {
    override fun toString(): String {
        return "Current model rows: $rows, columns: $columns, roadList: $roadList"
    }
}