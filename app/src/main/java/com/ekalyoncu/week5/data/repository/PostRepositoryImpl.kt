package com.ekalyoncu.week5.data.repository

import com.ekalyoncu.week5.data.data_source.local.AppDatabase
import com.ekalyoncu.week5.data.data_source.local.model.PostEntity
import com.ekalyoncu.week5.data.data_source.remote.PostService
import com.ekalyoncu.week5.data.data_source.remote.model.Post
import retrofit2.Call

class PostRepositoryImpl constructor(
    private val postService: PostService,
    private val appDatabase: AppDatabase
) : PostRepository {

    override fun getPostsFromRemote(): Call<List<Post>> {
        return postService.getPosts()
    }

    override fun getPostsFromLocal(): List<PostEntity> {
        return appDatabase.postDao().getPosts()
    }

    override fun savePosts(posts: List<PostEntity>) {
        return appDatabase.postDao().savePosts(posts)
    }

    override fun getFavoritePosts(): List<PostEntity> {
        return appDatabase.postDao().getFavoritePosts()
    }

    override fun updateFavorite(post: PostEntity) {
        return appDatabase.postDao().update(post)
    }

    override fun getPostById(id: Int): Call<Post> {
        return postService.getPostById(id)
    }
}