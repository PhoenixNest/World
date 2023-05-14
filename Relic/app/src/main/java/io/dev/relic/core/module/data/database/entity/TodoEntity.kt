package io.dev.relic.core.module.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity class for notes.
 *
 * @param id            Auto-generate id for each note
 * @param title         Title for each note, such as Today's Work List
 * @param content       Content for each note, such as Wash the bathroom
 * @param color         Color in the background of the note
 * @param timeStamp     Auto-adding times of each note
 * */
@Entity(tableName = "table_todo")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val content: String,
    val color: Int,
    val timeStamp: Long
)