package com.example.huntinbolo.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.huntinbolo.model.User
import com.example.huntinbolo.utils.RetrofitClient
import com.example.huntinbolo.utils.StatusCode
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.net.ssl.SSLEngineResult

class UserRepository {

    companion object {
        private val retrofit = RetrofitClient.getInstance()
        private val apiInterface = retrofit.create(ApiInterface::class.java)

        fun getUser() {

            apiInterface.getUsers().enqueue(object : Callback<List<User>> {
                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                    if (response.body() != null) {
                        //binding.profileBio.text = response.body().toString()
                    }
                }

                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    // TODO("Not yet implemented")
                }

            })
        }

        fun registerUser(map: HashMap<String, String>, userToken: MutableLiveData<String>) {

            apiInterface.registerUser(map).enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    //TODO("Not yet implemented")
                    Log.v("TEST", "PORCODI: " + response.code() + "\n" + response.body().toString())

                    val string = response.body().toString()

                    if (response.code() == StatusCode.Created.code) {
                        //converti con gson
                        userToken.value = response.body()?.token
                    } else if (response.code() == StatusCode.Conflict.code) {
                        //nn saprei
                    }

                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    // TODO("Not yet implemented")
                }

            })
        }


    }

}
