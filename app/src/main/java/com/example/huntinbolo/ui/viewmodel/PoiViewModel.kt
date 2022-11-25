package com.example.huntinbolo.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.huntinbolo.model.Poi
import com.example.huntinbolo.repository.PoiRepository

class PoiViewModel : ViewModel() {
    var poiList = MutableLiveData<ArrayList<Poi>>()
    var selectedPoi = MutableLiveData<Poi>()

    fun getPoi(token: String) {
        PoiRepository.getPois(token, poiList)
    }
}