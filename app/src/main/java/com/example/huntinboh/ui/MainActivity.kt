package com.example.huntinboh.ui

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.huntinboh.R
import com.example.huntinboh.databinding.ActivityMainBinding
import com.example.huntinboh.ui.viewmodel.UserViewModel
import com.example.huntinboh.utils.PreferenceHelper

class MainActivity : AppCompatActivity() {
    private val viewModel: UserViewModel by viewModels()
    private lateinit var sharedPref: SharedPreferences
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPref = PreferenceHelper.getSharedPreferences(this)
        viewModel.getUsers(sharedPref.getString(PreferenceHelper.USER_TOKEN, "")!!)
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}