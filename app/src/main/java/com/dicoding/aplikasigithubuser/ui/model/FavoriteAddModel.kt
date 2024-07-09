package com.dicoding.aplikasigithubuser.ui.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dicoding.aplikasigithubuser.data.local.FavoriteRepository
import com.dicoding.aplikasigithubuser.data.local.UserFavorite

class FavoriteAddModel(application: Application) : AndroidViewModel(application) {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun insert(favorite: UserFavorite) {
        mFavoriteRepository.insert(favorite)
    }

    fun delete(favorite: UserFavorite) {
        mFavoriteRepository.delete(favorite)
    }

    fun checkIsUserFavorite(username: String): LiveData<Boolean> {
        return mFavoriteRepository.checkIsUserFavorite(username)
    }
}