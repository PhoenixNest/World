package io.dev.android.game.test.room.data;

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class InventoryDatabase : RoomDatabase() {

    companion object {

        private const val DATABASE_NAME = "db_inventory"

        @Volatile
        private var INSTANCE: InventoryDatabase? = null

        fun getDatabase(context: Context): InventoryDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    InventoryDatabase::class.java,
                    DATABASE_NAME
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }

    abstract fun itemDao(): ItemDao

}
