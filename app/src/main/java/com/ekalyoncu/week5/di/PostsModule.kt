package com.ekalyoncu.week5.di

import com.ekalyoncu.week5.data.data_source.local.AppDatabase
import com.ekalyoncu.week5.data.data_source.remote.PostService
import com.ekalyoncu.week5.data.repository.PostRepository
import com.ekalyoncu.week5.data.repository.PostRepositoryImpl
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
    fun providePostRepository(postService: PostService, appDatabase: AppDatabase) : PostRepository {
        return PostRepositoryImpl(postService, appDatabase)
    }
}