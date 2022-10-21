package com.ekalyoncu.retrofitexample.data.repository

import com.ekalyoncu.retrofitexample.data.model.Users
import com.ekalyoncu.retrofitexample.data.remote.api.PostService
import com.ekalyoncu.retrofitexample.data.remote.api.UserService
import retrofit2.Call

class UserRepositoryImpl(
    private val userService: UserService,
):UserRepository {
    override fun getUsers(): Call<List<Users>> {
        return userService.getUsers()
    }
}