package com.example.huntinboh.model


data class User(
    var id: String?,
    var email: String,
    var username: String,
    var bio: String?,
    var password: String,
    var token: String
)
