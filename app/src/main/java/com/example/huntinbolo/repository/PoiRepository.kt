package com.example.huntinbolo.repository

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
            list: MutableLiveData<ArrayList<Poi>>
        ) {
            apiInterface.getOptimalPoi(token, info).enqueue(object : Callback<Poi> {
                override fun onResponse(call: Call<Poi>, response: Response<Poi>) {
                    when (response.code()) {
                        StatusCode.OK.code -> {
                            val list2 = ArrayList<Poi>()
                            list2.add(response.body()!!)
                            list.value = list2
                            val a = list.value
                        }
                    }

                }

                override fun onFailure(call: Call<Poi>, t: Throwable) {
                    // TODO("Not yet implemented")
                }

            })
        }
    }

}