package com.example.huntinboh.repository

import com.example.huntinboh.model.Poi
import com.example.huntinboh.model.User
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @GET("/user")
    fun getUsers(@Header("x-access-token") token: String): Call<Any>

    @GET("/poi")
    fun getPois(@Header("x-access-token") token: String): Call<List<Poi>>

    @GET("/poi/{category}")
    fun getPoiCategory(
        @Header("x-access-token") token: String,
        @Path("category") category: Int
    ): Call<List<Poi>>

    @POST("/poi/findOptimal")
    fun getOptimalPoi(
        @Header("x-access-token") token: String,
        @Body info: HashMap<String, Any>
    ): Call<Poi>

    @POST
    fun getOptimalPoiTrusted(
        @Url url: String,
        @Header("x-access-token") token: String,
        @Body info: HashMap<String, Any>
    ): Call<Poi>

    @POST("/user")
    fun registerUser(@Body user: HashMap<String, String>): Call<User>

    @DELETE("/user/{id}")
    fun deleteUser(@Path("id") id: String): Call<Any>

    @GET("user/{id}")
    fun getUserById(@Header("x-access-token") token: String, @Path("id") id: String): Call<User>

    @POST("/login")
    fun loginUser(
        @Header("x-access-token") token: String,
        @Body credentials: HashMap<String, String>
    ): Call<User>


}