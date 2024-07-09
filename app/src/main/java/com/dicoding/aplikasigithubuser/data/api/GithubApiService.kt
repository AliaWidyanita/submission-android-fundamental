package com.dicoding.aplikasigithubuser.data.api

import com.dicoding.aplikasigithubuser.data.Github
import com.dicoding.aplikasigithubuser.data.DetailUser
import com.dicoding.aplikasigithubuser.data.FollowingFollower
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApiService {
    @GET("search/users")
    fun getUsers(@Query("q") query: String): Call<Github>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailUser>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<FollowingFollower>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<FollowingFollower>>
}