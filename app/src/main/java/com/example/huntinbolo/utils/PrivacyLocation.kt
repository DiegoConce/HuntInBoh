package com.example.huntinbolo.utils

import android.util.Log
import com.google.android.gms.maps.model.LatLng
import kotlin.random.Random

class PrivacyLocation {
    companion object {

        fun dummpyUpdate(lat: Double, lon: Double): LatLng {

            val perturbation = 0.005

            val newLat = lat - perturbation + Math.random() * (2 * perturbation)
            val newLon = lon - perturbation + Math.random() * (2 * perturbation)

            Log.v("DUMMY", "Dummy New Lat ---> $newLat")
            Log.v("DUMMY", "Dummy New Lon ---> $newLon")

            return LatLng(newLat, newLon)
        }


        fun gpsPerturbation(lat: Double, lon: Double, n: Int): LatLng {
            Log.v("TEST", "Numero di cifre gps perturbation ---> $n")

            Log.v("TEST","GPS Perturb Lat ---> $lat")
            Log.v("TEST", "GPS Perturb Lon ---> $lon")

            var newLat = lat.toString()
            var newLon = lon.toString()
            val digitRandom = Math.random().toString().slice(2..5)

            newLat = newLat.slice(0..1) + newLat.slice(2..2 + n) + digitRandom
            newLon = newLon.slice(0..1)  + newLon.slice(2..2 + n) + digitRandom

            Log.v("TEST","GPS Perturb newLat ---> $newLat")
            Log.v("TEST", "GPS Perturb newLon ---> $newLon")

            return LatLng(newLat.toDouble(), newLon.toDouble())
        }


    }
}