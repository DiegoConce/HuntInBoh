package com.example.huntinbolo.utils

import com.google.android.gms.maps.model.LatLng
import kotlin.random.Random

class PrivacyLocation {
    companion object {

        fun dummpyUpdate(lat: Double, lon: Double, n: Int): LatLng {
            val updates = 10
            val positions = ArrayList<LatLng>()
            val perturbation = 0.005

            for (i in 1..updates) {
                val newLat = lat - perturbation + Math.random() * (2 * perturbation)
                val newLong = lon - perturbation + Math.random() * (2 * perturbation)
                positions.add(LatLng(newLat, newLong))
            }

            return LatLng(lat, lon)
        }


        // massimo 8 cifre da riv
        fun gpsPerturbation(lat: Double, lon: Double, n: Int): LatLng {
            var digitRandom = Math.random().toString().slice(2..8)

            val newLat = lat.toString().slice(0..2) + digitRandom

            digitRandom = Math.random().toString().slice(2..8)

            val newLong =lon.toString().slice(0..2) + digitRandom

            return LatLng(newLat.toDouble(), newLong.toDouble())
        }


    }
}