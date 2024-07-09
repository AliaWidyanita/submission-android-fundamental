package com.dicoding.aplikasigithubuser.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserFavorite::class], version = 1)
abstract class UserFavoriteDatabase : RoomDatabase() {
    abstract fun userFavoriteDao(): UserFavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: UserFavoriteDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): UserFavoriteDatabase {
            if (INSTANCE == null) {
                synchronized(UserFavoriteDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        UserFavoriteDatabase::class.java, "favorite_database"
                    )
                        .build()
                }
            }
            return INSTANCE as UserFavoriteDatabase
        }
    }
}