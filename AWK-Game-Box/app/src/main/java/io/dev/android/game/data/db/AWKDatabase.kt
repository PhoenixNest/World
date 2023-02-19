package io.dev.android.game.data.db

import android.content.Context
import androidx.room.*
import io.dev.android.game.data.db.one_line_finish.OneLineFinishConverter
import io.dev.android.game.data.db.one_line_finish.OneLineFinishDao
import io.dev.android.game.data.db.one_line_finish.entity.OneLineFinishEntity

@Database(entities = [OneLineFinishEntity::class], version = 1, exportSchema = false)
@TypeConverters(OneLineFinishConverter::class)
abstract class AWKDatabase : RoomDatabase() {

    abstract fun oneLineFinishDao(): OneLineFinishDao

    companion object {

        private const val DATABASE_NAME = "db_awk_game_box"

        @Volatile
        private var INSTANCE: AWKDatabase? = null

        fun getDatabase(context: Context): AWKDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    AWKDatabase::class.java,
                    DATABASE_NAME
                )
                    .build()
                    .also {
                        INSTANCE = it
                    }
            }
        }
    }
}