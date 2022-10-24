package com.ekalyoncu.week5.data.repository

import com.ekalyoncu.week5.data.data_source.remote.UserService
import com.ekalyoncu.week5.data.data_source.remote.model.User
import retrofit2.Call

class UserRepositoryImpl(
    private val userService: UserService,
): UserRepository {
    override fun getUsers(): Call<List<User>> {
        return userService.getUsers()
    }
}