package com.example.huntinbolo.repository

import com.example.huntinbolo.model.User
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET("/users")
    suspend fun getUser() : Response<List<User>>
}