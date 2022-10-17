package com.example.huntinbolo.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.huntinbolo.R
import com.example.huntinbolo.databinding.FragmentSignupBinding
import com.example.huntinbolo.model.User
import com.example.huntinbolo.repository.UserRepository
import com.example.huntinbolo.ui.viewmodel.UserViewModel

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignupBinding
    private val viewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        setListeners()
        return binding.root
    }

    private fun setListeners() {
        binding.signupBackButton.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }
        binding.signupButton.setOnClickListener {
            // findNavController().navigate(R.id.action_signUpFragment_to_bottomNavFragment)


            //  UserRepository.registerUser(userTest)

            viewModel.performRegisterUser(
                binding.signupEmail.text.toString().trim(),
                binding.signupUsername.text.toString().trim(),
                binding.signupBio.text.toString().trim(),
                binding.signupPass.text.toString().trim()
            )

        }
    }

    private fun checkValues(): Boolean {
        var passed = true

        if (binding.signupEmail.text.isNullOrEmpty()) {
            binding.signupEmail.error = "Inserisci la tua email"
            passed = false
        }

        if (binding.signupUsername.text.isNullOrEmpty()) {
            binding.signupUsername.error = "Inserisci il tuo username"
            passed = false
        }

        if (binding.signupPass.text.isNullOrEmpty()) {
            binding.signupPass.error = "Inserisci pass"
            passed = false
        }

        if (binding.signupConfirmPass.text.isNullOrEmpty()) {
            binding.signupConfirmPass.error = "conferma pass"
            passed = false
        }

        return passed
    }

}