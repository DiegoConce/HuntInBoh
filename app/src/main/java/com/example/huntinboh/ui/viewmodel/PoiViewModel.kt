package com.example.huntinboh.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.huntinboh.model.Poi
import com.example.huntinboh.model.TrustedOptions
import com.example.huntinboh.model.User
import com.example.huntinboh.repository.PoiRepository
import com.example.huntinboh.repository.UserRepository
import com.google.android.gms.maps.model.LatLng

class PoiViewModel : ViewModel() {
    var resMsg = MutableLiveData<String>()
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

    fun getOptimalPoi(token: String, latLng: LatLng, category: Int, rank: Int) {
        poiList.value?.clear()

        val map = HashMap<String, Any>()
        map["lat"] = latLng.latitude.toString()
        map["long"] = latLng.longitude.toString()
        map["category"] = category
        map["rank"] = rank

        PoiRepository.getOptimalPoi(token, map, poiList)
    }

    fun getOptimalPoiTrusted(
        token: String,
        latLng: LatLng,
        category: Int,
        rank: Int,
        trustedOptions: TrustedOptions
    ) {
        poiList.value?.clear()

        val map = HashMap<String, Any>()
        map["lat"] = latLng.latitude.toString()
        map["long"] = latLng.longitude.toString()
        map["category"] = category
        map["msgNo"] = trustedOptions.msgNo
        map["rank"] = rank
        map["anonimityLevel"] = trustedOptions.anonimityLevel
        map["spatialToleranceX"] = trustedOptions.spatialToleranceX
        map["spatialToleranceY"] = trustedOptions.spatialToleranceY
        map["temporalTolerance"] = trustedOptions.temporalTolerance
        map["requestsTolerance"] = trustedOptions.requestsTolerance

        PoiRepository.getOptimalPoiTrusted(token, map, poiList, resMsg)
    }

    fun getUserById(id: String, token: String) {
        UserRepository.getUserById(id, token, addedByUser)
    }
}