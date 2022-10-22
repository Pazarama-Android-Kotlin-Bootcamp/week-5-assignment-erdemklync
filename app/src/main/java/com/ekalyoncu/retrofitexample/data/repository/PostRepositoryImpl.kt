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

    override fun getFavorites(): List<PostEntity> {
        return postsDatabase.postDao().getAllPosts()
    }

    override fun getPostById(id: Int): Call<Post> {
        return postService.getPostById(id.toLong())
    }

    override fun getPostByIdForFavorite(id: Long): PostEntity? {
        return postsDatabase.postDao().getPostById(id)
    }

    override fun insertFavoritePost(post: PostEntity) {
        return postsDatabase.postDao().insert(post)
    }

    override fun deleteFavoritePost(post: PostEntity) {
        return postsDatabase.postDao().delete(post)
    }
}