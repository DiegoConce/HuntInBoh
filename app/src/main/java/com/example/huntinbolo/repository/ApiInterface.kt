package com.example.huntinbolo.repository

import com.example.huntinbolo.model.Poi
import com.example.huntinbolo.model.User
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @GET("/user")
    fun getUsers(@Header("x-access-token") token: String): Call<Any>

    @GET("/poi")
    fun getPois(@Header("x-access-token") token: String): Call<List<Poi>>

    @POST("/user")
    fun registerUser(@Body user: HashMap<String, String>): Call<User>

    @DELETE("/user/{id}")
    fun deleteUser(@Path("id") id: String): Call<Any>

    @POST("/login")
    fun loginUser(
        @Header("x-access-token") token: String,
        @Body credentials: HashMap<String, String>
    ): Call<User>


}