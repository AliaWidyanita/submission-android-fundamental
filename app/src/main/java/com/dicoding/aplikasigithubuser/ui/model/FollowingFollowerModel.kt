package com.dicoding.aplikasigithubuser.ui.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.aplikasigithubuser.data.FollowingFollower
import com.dicoding.aplikasigithubuser.data.api.GithubApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingFollowerModel: ViewModel() {

    private val _listFollowers = MutableLiveData<List<FollowingFollower>>()
    val listFollowers: LiveData<List<FollowingFollower>> = _listFollowers

    private val _listFollowing= MutableLiveData<List<FollowingFollower>>()
    val listFollowing: LiveData<List<FollowingFollower>> = _listFollowing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getFollowing(username: String) {
        _isLoading.value = true
        val client = GithubApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<FollowingFollower>> {
            override fun onResponse(
                call: Call<List<FollowingFollower>>,
                response: Response<List<FollowingFollower>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseDetail = response.body()
                    if (responseDetail != null) {
                        _listFollowing.value = response.body()
                    }
                } else {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<FollowingFollower>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getFollowers(username: String) {
        _isLoading.value = true
        val client = GithubApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<List<FollowingFollower>> {
            override fun onResponse(
                call: Call<List<FollowingFollower>>,
                response: Response<List<FollowingFollower>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseDetail = response.body()
                    if (responseDetail != null) {
                        _listFollowers.value = response.body()
                    }
                }
            }
            override fun onFailure(call: Call<List<FollowingFollower>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object{
        private const val TAG = "FollowingFollowerModel"
    }
}