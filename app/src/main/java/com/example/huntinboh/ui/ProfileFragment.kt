package com.example.huntinboh.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputFilter
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.huntinboh.R
import com.example.huntinboh.databinding.FragmentProfileBinding
import com.example.huntinboh.ui.viewmodel.UserViewModel
import com.example.huntinboh.utils.PreferenceHelper
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

        if (viewModel.trustedServer.value == true)
            showTrustedLayout()
        else
            hideTrustedLayout()

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
        binding.anonLevelNum.filters = arrayOf(MinMaxFilter(2, 10))
        binding.spatialTolXNum.filters = arrayOf(MinMaxFilter(1, 10000))
        binding.spatialTolYNum.filters = arrayOf(MinMaxFilter(1, 10000))
        binding.tempToleranceNum.filters = arrayOf(MinMaxFilter(1, 5))
        binding.reqToleranceNum.filters = arrayOf(MinMaxFilter(1, 50))

        setTextChangeListeners()

        binding.noPrivacySwitch.setOnClickListener {
            viewModel.setNoPrivacy()
            binding.trustedServerSwitch.isChecked = false
            binding.dummyUpdateSwitch.isChecked = false
            binding.gpsPerturbSwitch.isChecked = false
        }

        binding.dummyUpdateSwitch.setOnClickListener {
            viewModel.setDummyUpdate()
            binding.trustedServerSwitch.isChecked = false
            binding.noPrivacySwitch.isChecked = false
            binding.gpsPerturbSwitch.isChecked = false
        }

        binding.gpsPerturbSwitch.setOnClickListener {
            viewModel.setGpsPerturbation()
            binding.noPrivacySwitch.isChecked = false
            binding.dummyUpdateSwitch.isChecked = false
            binding.trustedServerSwitch.isChecked = false
        }

        binding.trustedServerSwitch.setOnClickListener {
            viewModel.setTrustedServer()
            binding.noPrivacySwitch.isChecked = false
            binding.gpsPerturbSwitch.isChecked = false
            binding.dummyUpdateSwitch.isChecked = false

            if (binding.trustedServerSwitch.isChecked)
                showTrustedLayout()
            else
                hideTrustedLayout()
        }
    }

    private fun showTrustedLayout() {
        binding.trustedLayout.visibility = View.VISIBLE

        binding.profileScrollView.fullScroll(ScrollView.FOCUS_DOWN)
    }

    private fun hideTrustedLayout() {
        binding.trustedLayout.visibility = View.GONE
        binding.profileScrollView.fullScroll(ScrollView.FOCUS_UP)
    }

    private fun setTextChangeListeners() {

        binding.dummyUpdateNum.doOnTextChanged { text, _, _, _ ->
            if (!text.isNullOrEmpty())
                viewModel.numberDummyUpdate.value = text.toString().toInt()
        }

        binding.gpsPerturbNum.doOnTextChanged { text, _, _, _ ->
            if (!text.isNullOrEmpty())
                viewModel.numberGpsPerturb.value = text.toString().toInt()
        }

        binding.anonLevelNum.doOnTextChanged { text, _, _, _ ->
            if (!text.isNullOrEmpty())
                viewModel.trustedOptions.value!!.anonimityLevel = text.toString().toInt()
        }

        binding.spatialTolXNum.doOnTextChanged { text, _, _, _ ->
            if (!text.isNullOrEmpty())
                viewModel.trustedOptions.value!!.spatialToleranceX = text.toString().toInt()
        }

        binding.spatialTolYNum.doOnTextChanged { text, _, _, _ ->
            if (!text.isNullOrEmpty())
                viewModel.trustedOptions.value!!.spatialToleranceY = text.toString().toInt()
        }

        binding.tempToleranceNum.doOnTextChanged { text, _, _, _ ->
            if (!text.isNullOrEmpty())
                viewModel.trustedOptions.value!!.temporalTolerance = text.toString().toInt()
        }

        binding.reqToleranceNum.doOnTextChanged { text, _, _, _ ->
            if (!text.isNullOrEmpty())
                viewModel.trustedOptions.value!!.requestsTolerance = text.toString().toInt()
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