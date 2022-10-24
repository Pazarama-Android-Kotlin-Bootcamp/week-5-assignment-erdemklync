package com.ekalyoncu.week5.data.repository

import com.ekalyoncu.week5.data.data_source.remote.model.User
import retrofit2.Call

interface UserRepository {
    fun getUsers(): Call<List<User>>
}