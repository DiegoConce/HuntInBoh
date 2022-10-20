package com.example.huntinbolo.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.huntinbolo.model.User
import com.example.huntinbolo.repository.UserRepository

class UserViewModel : ViewModel() {

    // val userList = MutableLiveData<ArrayList<>>

    var signUpResponse = MutableLiveData<Int>()
    var userToken = MutableLiveData<String>()

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

        UserRepository.registerUser(map, userToken)
    }
}