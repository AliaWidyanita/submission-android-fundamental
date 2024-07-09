package com.dicoding.aplikasigithubuser.ui.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.aplikasigithubuser.data.Github
import com.dicoding.aplikasigithubuser.data.FollowingFollower
import com.dicoding.aplikasigithubuser.data.api.GithubApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainModel : ViewModel() {

    private val _listUser = MutableLiveData<List<FollowingFollower>>()
    val listUser: LiveData<List<FollowingFollower>> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _noUser = MutableLiveData<Boolean>()
    val noUser: LiveData<Boolean> = _noUser

    fun searchUser(query: String) {
        _isLoading.value = true
        val client = GithubApiConfig.getApiService().getUsers(query)
        client.enqueue(object : Callback<Github> {
            override fun onResponse(
                call: Call<Github>,
                response: Response<Github>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseDetail = response.body()
                    if (responseDetail != null) {
                        val users = response.body()?.items
                        if (users.isNullOrEmpty()) {
                            _noUser.value = true
                        } else {
                            _listUser.value = users
                            _noUser.value = false
                        }
                    }
                } else {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<Github>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    private fun getUser() {
        searchUser("arif")
    }

    init{
        getUser()
    }

    companion object{
        private const val TAG = "MainModel"
    }
}