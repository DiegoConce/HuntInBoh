package com.example.huntinbolo.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.huntinbolo.model.Poi
import com.example.huntinbolo.model.User
import com.example.huntinbolo.repository.PoiRepository
import com.example.huntinbolo.repository.UserRepository

class PoiViewModel : ViewModel() {
    var poiList = MutableLiveData<ArrayList<Poi>>()
    var selectedPoi = MutableLiveData<Poi>()
    var addedByUser = MutableLiveData<User>()

    fun getPoi(token: String) {
        poiList.value?.clear()
        PoiRepository.getPois(token, poiList)
    }

    fun getPoiCategory(token: String, category: Int) {
        poiList.value?.clear()
        PoiRepository.getPoiCategory(token, category, poiList)
    }

    fun getOptimalPoi(token: String, lat: String, long: String, category: Int, rank: Int) {
        poiList.value?.clear()

        val map = HashMap<String, Any>()
        map["lat"] = lat
        map["long"] = long
        map["category"] = category
        map["rank"] = rank

       PoiRepository.getOptimalPoi(token, map, poiList)
    }

    fun getUserById(id: String, token: String) {
        UserRepository.getUserById(id, token, addedByUser)
    }
}