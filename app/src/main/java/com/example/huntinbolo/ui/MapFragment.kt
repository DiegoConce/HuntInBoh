package com.example.huntinbolo.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.huntinbolo.R
import com.example.huntinbolo.databinding.FragmentMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng

class MapFragment : Fragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentMapBinding
    private lateinit var map: GoogleMap

    private val bologna = LatLng(44.4949, 11.3426)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(inflater, container, false)

        val mapFragment = SupportMapFragment.newInstance()
        mapFragment.getMapAsync(this)

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.map_frame_layout, mapFragment)
            .commit()

        return binding.root
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(p0: GoogleMap) {
        // TODO("Not yet implemented")
        map = p0
        map.isMyLocationEnabled = true
        map.uiSettings.isCompassEnabled = false
        map.uiSettings.isMyLocationButtonEnabled = false

        binding.mapFabBtn.setOnClickListener {

           //map.animateCamera(CameraUpdateFactory.zoomIn())
            map.animateCamera(CameraUpdateFactory.zoomTo(10F), 2000, null)
            map.moveCamera(CameraUpdateFactory.newLatLng(bologna))

            val cameraPosition = CameraPosition.builder()
                .target(bologna)
                .zoom(17F)
                .bearing(90F)
                .tilt(30F)
                .build()

            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

            //Camera
        }

    }

}