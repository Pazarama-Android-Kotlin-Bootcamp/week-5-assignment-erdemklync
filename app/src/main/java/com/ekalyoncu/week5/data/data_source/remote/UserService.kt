package com.ekalyoncu.week5.data.data_source.remote

import com.ekalyoncu.week5.data.data_source.remote.model.User
import retrofit2.Call
import retrofit2.http.GET

interface UserService {
    @GET("users")
    fun getUsers(): Call<List<User>>
}
