package com.example.huntinbolo.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.huntinbolo.R
import com.example.huntinbolo.databinding.FragmentMapBinding
import com.example.huntinbolo.model.Poi
import com.example.huntinbolo.ui.viewmodel.PoiViewModel
import com.example.huntinbolo.ui.viewmodel.UserViewModel
import com.example.huntinbolo.utils.PoiCategory
import com.example.huntinbolo.utils.PreferenceHelper
import com.example.huntinbolo.utils.PrivacyLocation
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MapFragment : Fragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentMapBinding
    private lateinit var map: GoogleMap
    private lateinit var sharedPref: SharedPreferences
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val viewModel: PoiViewModel by activityViewModels()
    private val userVM: UserViewModel by activityViewModels()
    private var rankValue = 1
    private var selectedCategory = 1

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
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        val mapFragment = SupportMapFragment.newInstance()
        mapFragment.getMapAsync(this)
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.map_frame_layout, mapFragment)
            .commit()

        //viewModel.getPoi(sharedPref.getString(PreferenceHelper.USER_TOKEN, "")!!)

        return binding.root
    }


    @SuppressLint("MissingPermission")
    override fun onMapReady(p0: GoogleMap) {
        // TODO("Not yet implemented")
        map = p0

        viewModel.poiList.observe(viewLifecycleOwner) {
            loadMarkers(it)
        }

        showMyPosition()
        setClickListeners()
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

                // Permessi Concessi
                map.isMyLocationEnabled = true
                map.uiSettings.isCompassEnabled = false
                map.uiSettings.isMyLocationButtonEnabled = false
                fusedLocationClient.lastLocation.addOnSuccessListener {
                    if (it != null) {
                        setAnimatedPosition(LatLng(it.latitude, it.longitude), 17f)
                    }
                }
            }

            else -> {
                // Richiedo i permessi attraverso il prompt
                requestPermissions.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
        }
    }

    private fun setAnimatedPosition(pos: LatLng, zoom: Float) {
        map.animateCamera(CameraUpdateFactory.zoomTo(10F), 2000, null)
        map.moveCamera(CameraUpdateFactory.newLatLng(pos))

        val cameraPosition = CameraPosition.builder()
            .target(pos)
            .zoom(zoom)
            .bearing(90F)
            .tilt(30F)
            .build()

        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    @SuppressLint("MissingPermission")
    private fun setClickListeners() {
        binding.mapFabBtn.setOnClickListener {
            showMyPosition()
        }

        binding.nearbyButton.setOnClickListener {
            fusedLocationClient.lastLocation.addOnSuccessListener {
                sendLocation(it)
            }
        }

        binding.fountainChip.setOnClickListener {
            viewModel.getPoiCategory(
                sharedPref.getString(PreferenceHelper.USER_TOKEN, "")!!,
                PoiCategory.Fontanella.code
            )
            selectedCategory = PoiCategory.Fontanella.code
        }

        binding.benchChip.setOnClickListener {
            viewModel.getPoiCategory(
                sharedPref.getString(PreferenceHelper.USER_TOKEN, "")!!,
                PoiCategory.Panchine.code
            )
            selectedCategory = PoiCategory.Panchine.code
        }

        binding.bathroomChip.setOnClickListener {
            viewModel.getPoiCategory(
                sharedPref.getString(PreferenceHelper.USER_TOKEN, "")!!,
                PoiCategory.Bagni.code
            )
            selectedCategory = PoiCategory.Bagni.code
        }

        binding.parkChip.setOnClickListener {
            viewModel.getPoiCategory(
                sharedPref.getString(PreferenceHelper.USER_TOKEN, "")!!,
                PoiCategory.Parchi.code
            )
            selectedCategory = PoiCategory.Parchi.code
        }

        binding.trashChip.setOnClickListener {
            viewModel.getPoiCategory(
                sharedPref.getString(PreferenceHelper.USER_TOKEN, "")!!,
                PoiCategory.Cestini.code
            )
            selectedCategory = PoiCategory.Cestini.code
        }

        binding.daeChip.setOnClickListener {
            viewModel.getPoiCategory(
                sharedPref.getString(PreferenceHelper.USER_TOKEN, "")!!,
                PoiCategory.DAE.code
            )
            selectedCategory = PoiCategory.DAE.code
        }

        binding.rankSlider.addOnChangeListener { slider, value, fromUser ->
            rankValue = slider.value.toInt()
            Log.v("TEST", "rankvalue --- > " + rankValue)
        }
    }

    private fun sendLocation(location: Location) {

        if (userVM.noPrivacy.value == true) {
            viewModel.getOptimalPoi(
                sharedPref.getString(PreferenceHelper.USER_TOKEN, "")!!,
                LatLng(location.latitude, location.longitude),
                selectedCategory,
                rankValue
            )
        }

        if (userVM.dummyUpdate.value == true) {
            val x = userVM.numberDummyUpdate.value!!
            for (i in 1..x) { //n
                Log.v("DUMMY","Numero di dummy ---> $x")
                Log.v("DUMMY", "Indice ---> $i")
                Log.v("DUMMY","Dummy lat originale --->${location.latitude}")
                Log.v("DUMMY","Dummy lon originale --->${location.longitude}")

                viewModel.getOptimalPoi(
                    sharedPref.getString(PreferenceHelper.USER_TOKEN, "")!!,
                    PrivacyLocation.dummpyUpdate(location.latitude, location.longitude),
                    selectedCategory,
                    rankValue
                )
            }


            viewModel.poiList.observe(viewLifecycleOwner) {
                if (it.size == x) {
                    val e = it[Random.nextInt(it.size)]
                    it.clear()
                    it.add(e)
                    loadMarkers(it)
                }
            }
        }

        if (userVM.gpsPerturbation.value == true) {
            viewModel.getOptimalPoi(
                sharedPref.getString(PreferenceHelper.USER_TOKEN, "")!!,
                PrivacyLocation.gpsPerturbation(
                    location.latitude,
                    location.longitude,
                    userVM.numberGpsPerturb.value!!.toInt()
                ),
                selectedCategory,
                rankValue
            )
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
                    viewModel.getUserById(
                        item.added_by.toString(),
                        sharedPref.getString(PreferenceHelper.USER_TOKEN, "")!!,
                    )
                }
            }

            PoiBottomSheet().show(parentFragmentManager, PoiBottomSheet.TAG)
            true
        }
    }

}