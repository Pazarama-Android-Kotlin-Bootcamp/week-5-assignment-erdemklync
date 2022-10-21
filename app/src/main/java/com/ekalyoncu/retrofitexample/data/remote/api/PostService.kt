package com.ekalyoncu.retrofitexample.data.remote.api

import com.ekalyoncu.retrofitexample.data.model.Post
import com.ekalyoncu.retrofitexample.data.model.Users
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface PostService {
    @GET("posts")
    fun getPosts(): Call<List<Post>>
}
