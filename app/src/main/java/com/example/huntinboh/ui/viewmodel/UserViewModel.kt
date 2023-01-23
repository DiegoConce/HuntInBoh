package com.example.huntinboh.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.huntinboh.model.TrustedOptions
import com.example.huntinboh.repository.UserRepository
import com.example.huntinboh.repository.UserRepository.Companion.sha256

class UserViewModel : ViewModel() {

    var resMsg = MutableLiveData<String>()
    var noPrivacy = MutableLiveData(true)
    var dummyUpdate = MutableLiveData(false)
    var gpsPerturbation = MutableLiveData(false)
    var trustedServer = MutableLiveData(false)
    var numberDummyUpdate = MutableLiveData(1)
    var numberGpsPerturb = MutableLiveData(1)
    var trustedOptions = MutableLiveData(TrustedOptions())

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

    fun setNoPrivacy() {
        noPrivacy.value = true
        gpsPerturbation.value = false
        dummyUpdate.value = false
        trustedServer.value = false
    }

    fun setDummyUpdate() {
        noPrivacy.value  = false
        gpsPerturbation.value = false
        dummyUpdate.value = true
        trustedServer.value = false
    }

    fun setGpsPerturbation() {
        noPrivacy.value = false
        gpsPerturbation.value = true
        dummyUpdate.value = false
        trustedServer.value = false
    }

    fun setTrustedServer() {
        noPrivacy.value = false
        gpsPerturbation.value = false
        dummyUpdate.value = false
        trustedServer.value = true
    }

}