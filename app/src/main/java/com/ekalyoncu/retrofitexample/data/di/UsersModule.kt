package com.ekalyoncu.retrofitexample.data.di

import com.ekalyoncu.retrofitexample.data.remote.api.PostService
import com.ekalyoncu.retrofitexample.data.remote.api.UserService
import com.ekalyoncu.retrofitexample.data.repository.UserRepository
import com.ekalyoncu.retrofitexample.data.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
class UsersModule {
    @Provides
    fun provideApiService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Provides
    fun provideUserRepository(userService: UserService) : UserRepository {
        return UserRepositoryImpl(userService)
    }
}