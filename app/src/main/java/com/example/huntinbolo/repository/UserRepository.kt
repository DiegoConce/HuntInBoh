package com.example.huntinbolo.repository

import androidx.lifecycle.MutableLiveData
import com.example.huntinbolo.model.User
import com.example.huntinbolo.utils.PreferenceHelper
import com.example.huntinbolo.utils.RetrofitClient
import com.example.huntinbolo.utils.StatusCode
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.MessageDigest

class UserRepository {

    companion object {
        private val retrofit = RetrofitClient.getInstance()
        private val apiInterface = retrofit.create(ApiInterface::class.java)

        fun getUser(token: String, resMsg: MutableLiveData<String>) {
            apiInterface.getUsers(token).enqueue(object : Callback<Any> {
                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    when (response.code()) {
                        StatusCode.OK.code -> {
                            resMsg.value = response.message()
                        }
                        StatusCode.InternalServerError.code -> {
                            resMsg.value = ""
                        }
                    }
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {

                }
            })
        }

        fun getUserById(id: String, token: String, user: MutableLiveData<User>) {
            apiInterface.getUserById(token, id).enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    when (response.code()) {
                        StatusCode.OK.code -> {
                            user.value = response.body()
                        }
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                 //   TODO("Not yet implemented")
                }

            })
        }

        fun registerUser(map: HashMap<String, String>, resMsg: MutableLiveData<String>) {
            apiInterface.registerUser(map).enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {

                    when (response.code()) {
                        StatusCode.Created.code -> {
                            var bio = ""
                            if (response.body()?.bio != null) {
                                bio = response.body()!!.bio!!
                            }

                            PreferenceHelper.loadUser(
                                response.body()!!.username,
                                bio,
                                response.body()!!.email,
                                response.body()!!.token
                            )
                            resMsg.value = response.message()
                        }

                        StatusCode.Conflict.code -> {
                            resMsg.value = response.message()
                        }

                        StatusCode.InternalServerError.code -> {
                            resMsg.value = response.message()
                        }
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    resMsg.value = "Errore server, riprovare"
                }

            })
        }

        fun deleteUser(id: String) {
            apiInterface.deleteUser(id).enqueue(object : Callback<Any> {
                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    var a = id

                    when (response.code()) {
                        StatusCode.OK.code -> {

                        }
                        StatusCode.BadRequest.code -> {

                        }
                    }
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    //   TODO("Not yet implemented")
                }
            })
        }

        fun loginUser(
            token: String,
            credentials: HashMap<String, String>,
            resMsg: MutableLiveData<String>
        ) {
            apiInterface.loginUser(token, credentials).enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    when (response.code()) {
                        StatusCode.OK.code -> {
                            var bio = ""
                            if (response.body()?.bio != null) {
                                bio = response.body()!!.bio!!
                            }

                            PreferenceHelper.loadUser(
                                response.body()!!.username,
                                bio,
                                response.body()!!.email,
                                response.body()!!.token
                            )
                            resMsg.value = response.message()
                        }
                        StatusCode.BadRequest.code -> {
                            resMsg.value = response.message()
                        }
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    resMsg.value = "Errore server, riprovare"
                }

            })
        }

        fun String.sha256(): String {
            return hashString(this, "SHA-256")
        }

        fun String.md5(): String {
            return hashString(this, "MD5")
        }

        private fun hashString(input: String, algorithm: String): String {
            return MessageDigest
                .getInstance(algorithm)
                .digest(input.toByteArray())
                .fold("") { str, it -> str + "%02x".format(it) }
        }

    }

}
