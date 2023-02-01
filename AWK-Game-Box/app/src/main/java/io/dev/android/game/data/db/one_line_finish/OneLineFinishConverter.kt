package io.dev.android.game.data.db.one_line_finish

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.dev.android.game.data.db.one_line_finish.model.OneLineFinishRoadModel

class OneLineFinishConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromRoadModelString(roadModel: OneLineFinishRoadModel): String {
        return gson.toJson(roadModel)
    }

    @TypeConverter
    fun toRoadModel(string: String): OneLineFinishRoadModel {
        val type = object : TypeToken<OneLineFinishRoadModel>() {}.type
        return gson.fromJson(string, type)
    }
}