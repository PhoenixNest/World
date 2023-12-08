package io.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_todo")
data class TodoEntity(
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "subtitle")
    val subtitle: String,
    @ColumnInfo(name = "content")
    val content: String,
    @ColumnInfo(name = "priority")
    val priority: Int,
    @ColumnInfo(name = "color_hex")
    val color: Long,
    @ColumnInfo(name = "update_time")
    val updateTime: String,
    @ColumnInfo(name = "isFinish")
    val isFinish: Boolean
) {
    @ColumnInfo(name = "uid", index = true)
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}