package net.learn.jetpack.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import net.learn.jetpack.model.movies.Movie
import net.learn.jetpack.model.tv.TvShow

@Database(
    entities = [Movie::class, TvShow::class, RemoteKeysMovie::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun tvDao(): TvDao
    abstract fun remoteKeysMovieDao(): RemoteKeysMovieDao

//    companion object {
//        private var instance: AppDatabase? = null
//        fun getInstance(context: Context): AppDatabase {
//            instance?.let { return it }
//            instance = Room.databaseBuilder(
//                context.applicationContext, AppDatabase::class.java,
//                "db_apps.db"
//            ).build()
//            return instance!!
//        }
//    }

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context?): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context?) =
            Room.databaseBuilder(
                context!!.applicationContext,
                AppDatabase::class.java, "db_apps.db"
            )
                .build()
    }

}