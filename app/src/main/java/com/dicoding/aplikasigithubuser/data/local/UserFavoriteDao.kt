package com.dicoding.aplikasigithubuser.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserFavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favorite: UserFavorite)

    @Update
    fun update(favorite: UserFavorite)

    @Delete
    fun delete(favorite: UserFavorite)

    @Query("SELECT * from UserFavorite")
    fun getAllFavorite(): LiveData<List<UserFavorite>>


    @Query("SELECT EXISTS(SELECT 1 FROM UserFavorite WHERE username = :username LIMIT 1)")
    fun checkIsUserFavorite(username: String): LiveData<Boolean>
}