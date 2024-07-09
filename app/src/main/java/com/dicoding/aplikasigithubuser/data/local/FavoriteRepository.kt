package com.dicoding.aplikasigithubuser.data.local

import android.app.Application
import androidx.lifecycle.LiveData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository (application: Application) {

    private val userFavoriteDao: UserFavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = UserFavoriteDatabase.getDatabase(application)
        userFavoriteDao = db.userFavoriteDao()
    }

    fun getAllFavorite(): LiveData<List<UserFavorite>> = userFavoriteDao.getAllFavorite()

    fun insert(favorite: UserFavorite) {
        executorService.execute { userFavoriteDao.insert(favorite) }
    }

    fun delete(favorite: UserFavorite) {
        executorService.execute { userFavoriteDao.delete(favorite) }
    }

    fun checkIsUserFavorite(username: String): LiveData<Boolean> {
        return userFavoriteDao.checkIsUserFavorite(username)
    }
}