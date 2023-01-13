package com.example.huntinbolo.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.Spanned
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
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

        setInfo()
        setListeners()
        return binding.root
    }


    private fun setInfo() {
        binding.profileUsername.text = sharedPref.getString(PreferenceHelper.USER_NAME, "")!!
        binding.profileEmail.text = sharedPref.getString(PreferenceHelper.USER_EMAIL, "")!!

        if (sharedPref.getString(PreferenceHelper.USER_BIO, "")!!.isEmpty()) {
            binding.profileBio.text = "Nessuna descrizione inserita"
        } else {
            binding.profileBio.text = sharedPref.getString(PreferenceHelper.USER_BIO, "")!!
        }

        Glide.with(requireContext())
            .load("https://picsum.photos/400")
            .error(R.drawable.isoka)
            .into(binding.profileImage)

    }

    private fun setListeners() {
        binding.profileDeleteBtn.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.attenzione))
                .setMessage("Stai per eliminare per sempre il tuo accout, sei sicuro?")
                .setPositiveButton("Si") { _, _ ->
                    viewModel.deleteUser(sharedPref.getString(PreferenceHelper.USER_ID, "")!!)
                    requireActivity().finish()
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }
                .setNegativeButton("No", null)
                .show()
        }


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

        binding.dummyUpdateNum.filters = arrayOf(MinMaxFilter(1, 5))
        binding.gpsPerturbNum.filters = arrayOf(MinMaxFilter(1, 8))

        binding.dummyUpdateNum.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty())
                    viewModel.numberDummyUpdate.value = s.toString().toInt()
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        binding.gpsPerturbNum.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty())
                    viewModel.numberGpsPerturb.value = s.toString().toInt()
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        binding.noPrivacySwitch.setOnClickListener {
            viewModel.noPrivacy.value = true
            viewModel.gpsPerturbation.value = false
            viewModel.dummyUpdate.value = false
            binding.dummyUpdateSwitch.isChecked = false
            binding.gpsPerturbSwitch.isChecked = false
        }

        binding.dummyUpdateSwitch.setOnClickListener {
            viewModel.dummyUpdate.value = true
            viewModel.noPrivacy.value = false
            viewModel.gpsPerturbation.value = false
            binding.noPrivacySwitch.isChecked = false
            binding.gpsPerturbSwitch.isChecked = false
        }

        binding.gpsPerturbSwitch.setOnClickListener {
            viewModel.gpsPerturbation.value = true
            viewModel.noPrivacy.value = false
            viewModel.dummyUpdate.value = false
            binding.noPrivacySwitch.isChecked = false
            binding.dummyUpdateSwitch.isChecked = false
        }

        binding.trustedServerSwitch.setOnClickListener {
            viewModel.trustedServer.value = true
        }
    }

    inner class MinMaxFilter() : InputFilter {
        private var intMin: Int = 1
        private var intMax: Int = 1

        constructor(minValue: Int, maxValue: Int) : this() {
            this.intMin = minValue
            this.intMax = maxValue
        }

        override fun filter(
            source: CharSequence?,
            start: Int,
            end: Int,
            dest: Spanned?,
            dstart: Int,
            dend: Int
        ): CharSequence? {
            try {
                val input = Integer.parseInt(dest.toString() + source.toString())
                if (isInRange(intMin, intMax, input)) {
                    return null
                }
            } catch (e: java.lang.NumberFormatException) {
                e.printStackTrace()
            }
            return ""
        }

        private fun isInRange(a: Int, b: Int, c: Int): Boolean {
            return if (b > a) c in a..b else c in b..a
        }

    }

}