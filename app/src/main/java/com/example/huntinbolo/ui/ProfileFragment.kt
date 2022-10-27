package com.example.huntinbolo.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.huntinbolo.R
import com.example.huntinbolo.databinding.FragmentProfileBinding
import com.example.huntinbolo.ui.viewmodel.UserViewModel
import com.example.huntinbolo.utils.PreferenceHelper
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var sharedPref: SharedPreferences
    private val viewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        sharedPref = PreferenceHelper.getSharedPreferences(requireContext())

        //viewModel get users
        // set observable
        setView()
        setListeners()
        return binding.root
    }


    private fun setView() {

        binding.profileUsername.text = sharedPref.getString(PreferenceHelper.USER_NAME, "")!!
        binding.profileBio.text = sharedPref.getString(PreferenceHelper.USER_BIO, "")!!
        binding.profileEmail.text = sharedPref.getString(PreferenceHelper.USER_EMAIL, "")!!
        binding.profileToken.text = sharedPref.getString(PreferenceHelper.USER_TOKEN, "")!!
    }

    private fun setListeners() {
        binding.profileDeleteBtn.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.attenzione))
                .setMessage("Stai per eliminare per sempre il tuo accout, sei sicuro?")
                .setPositiveButton("Si") { _, _ ->

                    // viewModel.deleteUser(sharedPref.getString(PreferenceHelper.USER_NAME,"")!!)

                }
                .setNegativeButton("No", null)
                .show()
            //requireActivity().finish()
        }

        //chjeck locgged

        binding.profileLogoutBtn.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.attenzione)
                .setMessage("Stai per effettuare il logout, sei sicuro?")
                .setPositiveButton("Si") { _, _ ->
                    PreferenceHelper.clearPref()
                    requireActivity().finish()
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }
                .setNegativeButton("No", null)
                .show()
        }
    }

}