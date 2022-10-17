package com.example.huntinbolo.repository

import com.example.huntinbolo.model.User
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @GET("/users")
    fun getUsers(): Call<List<User>>

    @POST("/user")
    fun registerUser(@Body user: HashMap<String, String>): Call<Any>
}