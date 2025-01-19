package io.data.entity.todo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity(
    tableName = "table_todo",
    indices = [Index(value = ["uid"], unique = true)]
)
data class TodoEntity(
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "subtitle")
    val subtitle: String,
    @ColumnInfo(name = "content")
    val content: String,
    @ColumnInfo(name = "priority")
    val priority: Int,
    @ColumnInfo(name = "update_time")
    val updateTime: String = DateTimeFormatter.ofPattern("yyyy-MM-dd HH").format(LocalDateTime.now()),
    @ColumnInfo(name = "is_finished")
    val isFinish: Boolean
) {
    @ColumnInfo(name = "uid")
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}