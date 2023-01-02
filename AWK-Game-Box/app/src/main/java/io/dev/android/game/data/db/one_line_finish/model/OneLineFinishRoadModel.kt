package io.dev.android.game.data.db.one_line_finish.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.dev.android.game.util.Constants.TABLE_ONE_LINE_FINISH
import kotlinx.parcelize.Parcelize

@Entity(tableName = TABLE_ONE_LINE_FINISH)
@Parcelize
data class OneLineFinishRoadModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val rows: Int,
    val columns: Int,
    val roadList: List<Int>
) : Parcelable