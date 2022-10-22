package com.ekalyoncu.retrofitexample.data.repository

import com.ekalyoncu.retrofitexample.data.local.database.PostsDatabase
import com.ekalyoncu.retrofitexample.data.local.database.entity.PostEntity
import com.ekalyoncu.retrofitexample.data.remote.api.PostService
import com.ekalyoncu.retrofitexample.data.model.Post
import retrofit2.Call

class PostRepositoryImpl constructor(
    private val postService: PostService,
    private val postsDatabase: PostsDatabase
) : PostRepository {
    override fun getPosts(): Call<List<Post>> {
        return postService.getPosts()
    }

    override fun getPostById(id: Long): Call<Post> {
        //return postsDatabase.postDao().getPostById(id)
        return postService.getPostById(id)
    }

    override fun insertFavoritePost(post: PostEntity) {
        return postsDatabase.postDao().insert(post)
    }

    override fun deleteFavoritePost(post: PostEntity) {
        return postsDatabase.postDao().delete(post)
    }
}