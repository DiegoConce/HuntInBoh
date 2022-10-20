package com.example.huntinbolo.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.huntinbolo.databinding.FragmentProfileBinding
import com.example.huntinbolo.repository.ApiInterface
import com.example.huntinbolo.ui.viewmodel.UserViewModel
import com.example.huntinbolo.utils.RetrofitClient
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val viewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        //viewModel get users
        // set observable

        setListeners()
        return binding.root
    }


    private fun setListeners() {
        binding.profileDeleteBtn.setOnClickListener {
            val builder = MaterialAlertDialogBuilder(requireContext())
                .setTitle("aooo")
                .setMessage("messaggio")
                .setPositiveButton("Yes", null)
                .setNegativeButton("No", null)
                .show()
            //requireActivity().finish()
        }
    }

}