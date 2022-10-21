package com.ekalyoncu.retrofitexample.data.remote.api

import com.ekalyoncu.retrofitexample.data.model.Users
import retrofit2.Call
import retrofit2.http.GET

interface UserService {
    @GET("users")
    fun getUsers(): Call<List<Users>>
}
