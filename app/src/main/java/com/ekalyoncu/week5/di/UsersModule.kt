package com.ekalyoncu.week5.di

import com.ekalyoncu.week5.data.repository.UserRepository
import com.ekalyoncu.week5.data.repository.UserRepositoryImpl
import com.ekalyoncu.week5.data.data_source.remote.UserService
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