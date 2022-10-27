package com.example.huntinbolo.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.huntinbolo.repository.UserRepository

class UserViewModel : ViewModel() {

    var resMsg = MutableLiveData<String>()

    fun performRegisterUser(
        email: String,
        username: String,
        bio: String,
        password: String,
    ) {
        val map = HashMap<String, String>()
        map["email"] = email
        map["username"] = username
        map["bio"] = bio
        map["password"] = password

        UserRepository.registerUser(map, resMsg)
    }

    fun performLogin(token: String, email: String, password: String) {
        val map = HashMap<String, String>()
        map["email"] = email
        map["password"] = password

        UserRepository.loginUser(token, map, resMsg)
    }

    fun deleteUser(username: String) {
        UserRepository.deleteUser(username)
    }

    fun getUsers(token: String) {
        UserRepository.getUser(token, resMsg)
    }
}