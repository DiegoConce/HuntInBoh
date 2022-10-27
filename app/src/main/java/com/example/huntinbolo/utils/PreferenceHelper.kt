package com.example.huntinbolo.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.huntinbolo.MainApplication
import com.example.huntinbolo.model.User

class PreferenceHelper {
    companion object {
        const val USER_ID = "USERID"
        const val USER_NAME = "USERNAME"
        const val USER_EMAIL = "USEREMAIL"
        const val USER_TOKEN = "USERTOKEN"
        const val USER_BIO = "USERBIO"

        fun getSharedPreferences(context: Context): SharedPreferences {
            return context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        }

        fun loadUser(username: String, bio: String, email: String, token: String) {
            val pref = getSharedPreferences(MainApplication.applicationContext()!!)

            with(pref.edit()) {
                putString(USER_NAME, username)
                putString(USER_BIO, bio)
                putString(USER_EMAIL, email)
                putString(USER_TOKEN, token)

                apply()
            }
        }

        fun clearPref() {
            val pref = getSharedPreferences(MainApplication.applicationContext()!!)

            with(pref.edit()) {
                putString(USER_NAME, "")
                putString(USER_BIO, "")
                putString(USER_EMAIL, "")
                putString(USER_TOKEN, "")


                apply()
            }
        }
    }
}