package com.example.huntinbolo.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import com.example.huntinbolo.R
import com.example.huntinbolo.databinding.FragmentMapBinding
import com.example.huntinbolo.model.Poi
import com.example.huntinbolo.ui.viewmodel.PoiViewModel
import com.example.huntinbolo.utils.PreferenceHelper
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.data.geojson.GeoJsonLayer
import org.json.JSONObject

class MapFragment : Fragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentMapBinding
    private lateinit var map: GoogleMap
    private lateinit var sharedPref: SharedPreferences

    private val viewModel: PoiViewModel by activityViewModels()


    private val bologna = LatLng(44.4949, 11.3426)

    private val requestPermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { perms ->
            perms.entries.forEach {
                val isGranted = it.value
                if (isGranted) {
                    // Permission is granted
                } else {
                    // Permission is denied
                    Toast.makeText(requireContext(), "Dacci i permessi, nabbo", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        sharedPref = PreferenceHelper.getSharedPreferences(requireContext())

        val mapFragment = SupportMapFragment.newInstance()
        mapFragment.getMapAsync(this)
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.map_frame_layout, mapFragment)
            .commit()

        viewModel.getPoi(sharedPref.getString(PreferenceHelper.USER_TOKEN, "")!!)

        return binding.root
    }


    @SuppressLint("MissingPermission")
    override fun onMapReady(p0: GoogleMap) {
        // TODO("Not yet implemented")
        map = p0

        showMyPosition()

        viewModel.poiList.observe(viewLifecycleOwner) {
            loadMarkers(it)
        }

        binding.mapFabBtn.setOnClickListener {
            map.animateCamera(CameraUpdateFactory.zoomTo(10F), 2000, null)
            map.moveCamera(CameraUpdateFactory.newLatLng(bologna))

            val cameraPosition = CameraPosition.builder()
                .target(bologna)
                .zoom(17F)
                .bearing(90F)
                .tilt(30F)
                .build()

            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        }
    }

    private fun showMyPosition() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {

                //Permessi Concessi
                map.isMyLocationEnabled = true
                map.uiSettings.isCompassEnabled = false
                map.uiSettings.isMyLocationButtonEnabled = false
            }

            else -> {
                //Richiedo i permessi attraverso il prompt
                requestPermissions.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
        }
    }

    @SuppressLint("PotentialBehaviorOverride")
    private fun loadMarkers(list: ArrayList<Poi>) {
        map.clear()
        for (item in list) {
            map.addMarker(
                MarkerOptions()
                    .position(LatLng(item.lat.toDouble(), item.long.toDouble()))
                    .title(item.id.toString())
            )
        }

        map.setOnMarkerClickListener {
            for (item in list) {
                if (item.id == it.title!!.toInt()) {
                    viewModel.selectedPoi.value = item
                }
            }

            PoiBottomSheet().show(parentFragmentManager, PoiBottomSheet.TAG)
            true
        }
    }

}