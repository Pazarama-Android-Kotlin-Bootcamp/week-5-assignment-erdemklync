package com.ekalyoncu.retrofitexample.data.repository

import com.ekalyoncu.retrofitexample.data.local.database.entity.PostEntity
import com.ekalyoncu.retrofitexample.data.model.Post
import retrofit2.Call

interface PostRepository {
    fun getPosts(): Call<List<Post>>
    fun getPostById(id: Long): PostEntity?
    fun insertFavoritePost(post: PostEntity)
    fun deleteFavoritePost(post: PostEntity)
}