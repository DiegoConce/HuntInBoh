package com.example.huntinbolo.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.huntinbolo.databinding.FragmentProfileBinding
import com.example.huntinbolo.repository.ApiInterface
import com.example.huntinbolo.utils.RetrofitClient
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding


    //https://static.wikia.nocookie.net/hunterxhunter/images/b/bd/HxH2011_EP147_Killua_Portrait.png/revision/latest/scale-to-width-down/1000?cb=20220624211000

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        getUser()
        return binding.root
    }

    private fun getUser() {
        val retrofit = RetrofitClient.getInstance()
        val apiInterface = retrofit.create(ApiInterface::class.java)

        lifecycleScope.launchWhenCreated {
            try {
                val response = apiInterface.getUser()

                Log.v("TEST", response.body().toString())

                if (response.isSuccessful) {
                    val json = Gson().toJson(response.body())


                    Glide.with(requireActivity())
                        .load("https://top-mmo.fr/wp-content/uploads/2022/07/8af85-16569274154588-1920.jpg")
                        .into(binding.imageViewTest)
                }else {
                    Toast.makeText(
                        requireContext(),
                        response.errorBody().toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            } catch (Ex: Exception) {
                Log.e("ERROR", Ex.localizedMessage)
            }
        }
    }

}