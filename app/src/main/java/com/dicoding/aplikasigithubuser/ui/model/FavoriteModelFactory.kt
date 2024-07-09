package com.dicoding.aplikasigithubuser.ui.model

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FavoriteModelFactory private constructor(private val mApplication: Application) :
    ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: FavoriteModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): FavoriteModelFactory {
            if (INSTANCE == null) {
                synchronized(FavoriteModelFactory::class.java) {
                    INSTANCE = FavoriteModelFactory(application)
                }
            }
            return INSTANCE as FavoriteModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteModel::class.java)) {
            return FavoriteModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(FavoriteAddModel::class.java)) {
            return FavoriteAddModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}