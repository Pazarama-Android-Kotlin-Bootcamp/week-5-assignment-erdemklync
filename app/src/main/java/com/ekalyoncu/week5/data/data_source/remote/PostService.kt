package com.ekalyoncu.week5.data.data_source.remote

import com.ekalyoncu.week5.data.data_source.remote.model.Post
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PostService {
    @GET("posts")
    fun getPosts(): Call<List<Post>>

    @GET("posts/{id}")
    fun getPostById(
        @Path("id") id: Int,
    ): Call<Post>
}
