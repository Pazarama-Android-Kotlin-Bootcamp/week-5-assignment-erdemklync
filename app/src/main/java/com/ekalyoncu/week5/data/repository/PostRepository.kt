package com.ekalyoncu.week5.data.repository

import com.ekalyoncu.week5.data.data_source.local.model.PostEntity
import com.ekalyoncu.week5.data.data_source.remote.model.Post
import retrofit2.Call

interface PostRepository {
    fun getPostsFromRemote(): Call<List<Post>>
    fun getPostsFromLocal(): List<PostEntity>
    fun savePosts(posts: List<PostEntity>)
    fun getFavoritePosts(): List<PostEntity>
    fun updateFavorite(post: PostEntity)
    fun getPostById(id: Int): Call<Post>
}