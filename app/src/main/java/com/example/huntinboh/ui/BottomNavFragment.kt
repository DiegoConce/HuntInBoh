package com.example.huntinboh.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.huntinboh.R
import com.example.huntinboh.databinding.FragmentBottomNavBinding
import com.example.huntinboh.utils.setupWithNavController

class BottomNavFragment : Fragment() {
    private lateinit var binding: FragmentBottomNavBinding

    private val bottomNavSelectedItemIdKey = "BOTTOM_NAV_SELECTED_ITEM_ID_KEY"
    private var bottomNavSelectedItemId = R.id.mapFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBottomNavBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        savedInstanceState?.let {
            bottomNavSelectedItemId =
                savedInstanceState.getInt(bottomNavSelectedItemIdKey, bottomNavSelectedItemId)
        }

        setUpBottomNavBar()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(bottomNavSelectedItemIdKey, bottomNavSelectedItemId)
        super.onSaveInstanceState(outState)
    }

    private fun setUpBottomNavBar() {
        val navGraphIds = listOf(
            R.navigation.nav_map,
            R.navigation.nav_profile
        )

        binding.bottomNavigationView.selectedItemId = bottomNavSelectedItemId

        val controller = binding.bottomNavigationView.setupWithNavController(
            fragmentManager = childFragmentManager,
            navGraphIds = navGraphIds,
            containerId = R.id.bottom_nav_container,
            intent = requireActivity().intent
        )

        controller.observe(viewLifecycleOwner) {
            bottomNavSelectedItemId = it.graph.id
        }
    }
}