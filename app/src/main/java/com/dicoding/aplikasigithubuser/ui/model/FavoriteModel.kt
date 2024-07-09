package com.dicoding.aplikasigithubuser.ui.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dicoding.aplikasigithubuser.data.local.FavoriteRepository
import com.dicoding.aplikasigithubuser.data.local.UserFavorite

class FavoriteModel(application: Application) : AndroidViewModel(application) {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun getAllFavorite(): LiveData<List<UserFavorite>> = mFavoriteRepository.getAllFavorite()
}