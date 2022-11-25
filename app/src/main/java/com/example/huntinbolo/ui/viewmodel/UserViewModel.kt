package com.example.huntinbolo.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.huntinbolo.repository.UserRepository
import com.example.huntinbolo.repository.UserRepository.Companion.sha256

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
        map["password"] = password.sha256()

        UserRepository.registerUser(map, resMsg)
    }

    fun performLogin(token: String, email: String, password: String) {
        val map = HashMap<String, String>()
        map["email"] = email
        map["password"] = password.sha256()

        UserRepository.loginUser(token, map, resMsg)
    }

    fun deleteUser(id: String) {
        UserRepository.deleteUser(id)
    }

    fun getUsers(token: String) {
        UserRepository.getUser(token, resMsg)
    }
}