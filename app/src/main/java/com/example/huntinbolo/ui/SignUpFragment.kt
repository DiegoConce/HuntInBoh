package com.example.huntinbolo.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.huntinbolo.R
import com.example.huntinbolo.databinding.FragmentSignupBinding

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignupBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        setListeners()
        return binding.root
    }

    private fun setListeners() {
        binding.signupBackButton.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }
        binding.signupButton.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_bottomNavFragment)
        }
    }

}