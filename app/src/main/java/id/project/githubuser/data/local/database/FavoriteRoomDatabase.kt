package id.project.githubuser.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.project.githubuser.data.local.dao.FavoriteGithubUserDao
import id.project.githubuser.data.local.model.FavoriteGithubUser

@Database(entities = [FavoriteGithubUser::class], version = 1)
abstract class FavoriteRoomDatabase: RoomDatabase() {
    abstract fun favoriteDao(): FavoriteGithubUserDao

    companion object {
        @Volatile
        private var INSTANCE: FavoriteRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): FavoriteRoomDatabase {
            if (INSTANCE == null) {
                synchronized(FavoriteRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FavoriteRoomDatabase::class.java,
                        "favorite_database"
                    ).build()
                }
            }
            return INSTANCE as FavoriteRoomDatabase
        }
    }
}