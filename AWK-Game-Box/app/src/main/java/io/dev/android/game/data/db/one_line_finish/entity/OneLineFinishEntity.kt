package io.dev.android.game.data.db.one_line_finish.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import io.dev.android.game.data.db.one_line_finish.model.OneLineFinishRoadModel
import io.dev.android.game.util.Constants.TABLE_ONE_LINE_FINISH

@Entity(tableName = TABLE_ONE_LINE_FINISH)
class OneLineFinishEntity(
    var dataModel: OneLineFinishRoadModel
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}