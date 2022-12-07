package com.example.huntinbolo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.huntinbolo.databinding.BottomSheetPoiBinding
import com.example.huntinbolo.ui.viewmodel.PoiViewModel
import com.example.huntinbolo.utils.PoiCategory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PoiBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetPoiBinding
    private lateinit var tagAdapter: TagAdapter
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
            setTitle(it.category, it.id)
            binding.poiRank.text = "Rank: " + it.rank

            if (it.description.isNullOrEmpty()) {
                binding.poiDescription.text = "Nessuna descrizione disponibile"
            } else {
                binding.poiDescription.text = it.description
            }

            binding.addedBy.text = viewModel.addedByUser.value?.username.toString()
            setDistanceCard(it.distance, it.duration)
            setRecyclerView(it.tags)
        }


    }

    private fun setTitle(category: String, id: Int) {
        when (category.toInt()) {
            PoiCategory.Fontanella.code -> binding.poiName.text = "Fontanella " + id
            PoiCategory.Panchine.code -> binding.poiName.text = "Panchina " + id
            PoiCategory.Bagni.code -> binding.poiName.text = "Bagno " + id
            PoiCategory.Parchi.code -> binding.poiName.text = "Parco " + id
            PoiCategory.Cestini.code -> binding.poiName.text = "Cestino " + id
            PoiCategory.DAE.code -> binding.poiName.text = "Defribillatore " + id
        }
    }

    private fun setDistanceCard(distance: Float?, duration: Float?) {
        if (distance == null) {
            binding.poiDistanceCard.visibility = View.GONE
        } else {
            binding.poiDistanceCard.visibility = View.VISIBLE
            binding.poiDistance.text = distance.toString() + " metri"
            binding.poiDuration.text = duration.toString() + " minuti"
        }
    }

    private fun setRecyclerView(list: HashMap<String, String>) {
        if (list.isEmpty()) {
            binding.noTagCard.visibility = View.VISIBLE
        } else {
            binding.noTagCard.visibility = View.GONE
            tagAdapter = TagAdapter()
            tagAdapter.setData(list)
            binding.tagRecyclerView.adapter = tagAdapter
            binding.tagRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
    }


    companion object {
        const val TAG = "PoiBottomSheet"
    }

}