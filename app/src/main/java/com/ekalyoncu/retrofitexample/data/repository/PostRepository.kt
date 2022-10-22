package com.ekalyoncu.retrofitexample.data.repository

import com.ekalyoncu.retrofitexample.data.local.database.entity.PostEntity
import com.ekalyoncu.retrofitexample.data.model.Post
import retrofit2.Call

interface PostRepository {
    fun getPosts(): Call<List<Post>>
    fun getFavorites(): List<PostEntity>
    fun getPostById(id: Long): Call<Post>
    fun insertFavoritePost(post: PostEntity)
    fun deleteFavoritePost(post: PostEntity)
}