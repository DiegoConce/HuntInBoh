package com.example.huntinbolo.repository

import android.util.Log
import com.example.huntinbolo.model.User
import com.example.huntinbolo.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        fun registerUser(map: HashMap<String, String>) {

            apiInterface.registerUser(map).enqueue(object : Callback<Any> {
                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    //TODO("Not yet implemented")
                    Log.v("TEST", "SoNO qui")
                    Log.v("TEST", response.body().toString())

                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    // TODO("Not yet implemented")
                }

            })
        }


    }

}
