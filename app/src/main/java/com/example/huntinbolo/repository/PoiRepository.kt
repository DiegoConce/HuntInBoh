package com.example.huntinbolo.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.huntinbolo.model.Poi
import com.example.huntinbolo.utils.RetrofitClient
import com.example.huntinbolo.utils.StatusCode
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PoiRepository {

    companion object {
        private val retrofit = RetrofitClient.getInstance()
        private val apiInterface = retrofit.create(ApiInterface::class.java)

        fun getPois(token: String, list: MutableLiveData<ArrayList<Poi>>) {
            apiInterface.getPois(token).enqueue(object : Callback<List<Poi>> {
                override fun onResponse(call: Call<List<Poi>>, response: Response<List<Poi>>) {
                    when (response.code()) {
                        StatusCode.OK.code -> {
                            list.value = response.body()!!.toCollection(ArrayList())
                        }
                    }
                }

                override fun onFailure(call: Call<List<Poi>>, t: Throwable) {
                    // TODO("Not yet implemented")
                }

            }
            )
        }

        fun getPoiCategory(token: String, category: Int, list: MutableLiveData<ArrayList<Poi>>) {
            apiInterface.getPoiCategory(token, category).enqueue(object : Callback<List<Poi>> {
                override fun onResponse(call: Call<List<Poi>>, response: Response<List<Poi>>) {
                    when (response.code()) {
                        StatusCode.OK.code -> {
                            list.value = response.body()!!.toCollection(ArrayList())
                        }
                    }
                }

                override fun onFailure(call: Call<List<Poi>>, t: Throwable) {
                    // TODO("Not yet implemented")
                }
            })
        }

        fun getOptimalPoi(
            token: String,
            info: HashMap<String, Any>,
            list: MutableLiveData<ArrayList<Poi>>,
        ) {
            apiInterface.getOptimalPoi(token, info).enqueue(object : Callback<Poi> {
                override fun onResponse(call: Call<Poi>, response: Response<Poi>) {
                    when (response.code()) {
                        StatusCode.OK.code -> {
                            val old = list.value.orEmpty()
                            list.value = ArrayList(old).apply {
                                add(response.body()!!)
                            }
                        }
                    }

                }

                override fun onFailure(call: Call<Poi>, t: Throwable) {
                    // TODO("Not yet implemented")
                }

            })
        }

        fun getOptimalPoiTrusted(
            token: String,
            info: HashMap<String, Any>,
            list: MutableLiveData<ArrayList<Poi>>,
            resMsg: MutableLiveData<String>
        ) {
            val trustedUrl = "https://quiver-oval-tarragon.glitch.me/poi/findOptimal"

            apiInterface.getOptimalPoiTrusted(trustedUrl, token, info)
                .enqueue(object : Callback<Poi> {
                    override fun onResponse(call: Call<Poi>, response: Response<Poi>) {
                        when (response.code()) {
                            StatusCode.OK.code -> {
                                Log.v("TEST", "FUNZIAAAaaaaaaa")
                                val old = list.value.orEmpty()
                                list.value = ArrayList(old).apply {
                                    add(response.body()!!)
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<Poi>, t: Throwable) {
                        resMsg.value = t.message
                    }

                })
        }
    }

}