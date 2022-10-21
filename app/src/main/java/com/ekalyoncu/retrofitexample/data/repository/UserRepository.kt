package com.ekalyoncu.retrofitexample.data.repository

import com.ekalyoncu.retrofitexample.data.model.Users
import retrofit2.Call

interface UserRepository {
    fun getUsers(): Call<List<Users>>
}