package com.example.huntinbolo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.activityViewModels
import com.example.huntinbolo.databinding.BottomSheetPoiBinding
import com.example.huntinbolo.ui.viewmodel.PoiViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PoiBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetPoiBinding
    private val viewModel: PoiViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetPoiBinding.inflate(inflater, container, false)

        loadInfo()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun loadInfo() {
        viewModel.selectedPoi.observe(viewLifecycleOwner) {
            binding.poiName.text = it.name
            binding.poiRank.text = "Rank: " + it.rank
            binding.poiDescription.text = it.description
            binding.poiStatus.text = it.operational_status
        }
    }


    companion object {
        const val TAG = "PoiBottomSheet"
    }

}