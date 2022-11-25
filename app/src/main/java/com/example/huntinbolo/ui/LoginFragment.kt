package com.example.huntinbolo.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.huntinbolo.R
import com.example.huntinbolo.databinding.FragmentLoginBinding
import com.example.huntinbolo.ui.viewmodel.UserViewModel
import com.example.huntinbolo.utils.PreferenceHelper

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var sharedPref: SharedPreferences
    private val viewModel: UserViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        sharedPref = PreferenceHelper.getSharedPreferences(requireContext())
        checkIfLogged()
        setListeners()
        setObservableVM()

        return binding.root
    }

    private fun setListeners() {
        binding.loginEnterBtn.setOnClickListener {
            val email = binding.loginEmail.text.toString().trim()
            val pass = binding.loginPassword.text.toString().trim()
            viewModel.performLogin(
                sharedPref.getString(PreferenceHelper.USER_TOKEN, "")!!,
                email,
                pass
            )
        }

        binding.loginSignupButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }
    }

    private fun setObservableVM() {
        viewModel.resMsg.value = ""
        viewModel.resMsg.observe(viewLifecycleOwner) {
            if (it == "OK") {
                findNavController().navigate(R.id.action_loginFragment_to_bottomNavFragment)
            } else if (it != "") {
                binding.loginChipError.visibility = View.VISIBLE
                binding.loginChipError.text = it
            } else {
                binding.loginChipError.visibility = View.GONE
            }
        }
    }

    private fun checkIfLogged() {
        viewModel.resMsg.value = ""
        viewModel.getUsers(sharedPref.getString(PreferenceHelper.USER_TOKEN, "")!!)
    }
}