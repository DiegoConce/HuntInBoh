package com.example.huntinbolo.utils

import com.google.android.gms.maps.model.LatLng
import kotlin.random.Random

class PrivacyLocation {
    companion object {

        // dummy or perturbate?
        fun dummpyUpdate(lat: Double, lon: Double): LatLng {
            val updates = 10
            val positions = ArrayList<LatLng>()
            val perturbation = 0.005

            for (i in 1..updates) {
                val newLat = lat - perturbation + Math.random() * (2 * perturbation)
                val newLong = lon - perturbation + Math.random() * (2 * perturbation)
                positions.add(LatLng(newLat,newLong))
            }

            return LatLng(lat, lon)
        }




    }
}