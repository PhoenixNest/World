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

        private const val DB_NAME = "db_awk"

        @Volatile
        private var INSTANCE: AWKDatabase? = null

        fun getDatabase(context: Context): AWKDatabase {
            val temp = INSTANCE
            if (temp != null) {
                return temp
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AWKDatabase::class.java,
                    DB_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}