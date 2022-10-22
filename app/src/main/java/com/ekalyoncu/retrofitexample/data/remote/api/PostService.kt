package com.ekalyoncu.retrofitexample.data.remote.api

import com.ekalyoncu.retrofitexample.data.model.Post
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PostService {
    @GET("posts")
    fun getPosts(): Call<List<Post>>

    @GET("posts")
    fun getPostById(
        @Query("id") id: Long,
    ): Call<Post>
}
