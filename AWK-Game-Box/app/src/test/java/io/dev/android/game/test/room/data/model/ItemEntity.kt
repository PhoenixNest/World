package io.dev.android.game.test.room.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_items")
data class ItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val price: Double,
    val quantity: Int
)