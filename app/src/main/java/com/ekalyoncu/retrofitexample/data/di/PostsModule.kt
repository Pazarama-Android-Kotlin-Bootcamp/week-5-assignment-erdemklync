package com.ekalyoncu.retrofitexample.data.di

import com.ekalyoncu.retrofitexample.data.local.database.PostsDatabase
import com.ekalyoncu.retrofitexample.data.remote.api.PostService
import com.ekalyoncu.retrofitexample.data.repository.PostRepository
import com.ekalyoncu.retrofitexample.data.repository.PostRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
class PostsModule {
    @Provides
    fun provideApiService(retrofit: Retrofit) : PostService {
        return retrofit.create(PostService::class.java)
    }

    @Provides
    fun providePostRepository(postService: PostService, postsDatabase: PostsDatabase) : PostRepository {
        return PostRepositoryImpl(postService, postsDatabase)
    }
}