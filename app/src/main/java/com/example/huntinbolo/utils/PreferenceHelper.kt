package com.example.huntinbolo.utils

import android.content.Context
import android.content.SharedPreferences

class PreferenceHelper {
    companion object {
        const val USER_ID = "USERID"
        const val USER_NAME = "USERNAME"
        const val USER_TOKEN = "USERTOKEN"

        fun getSharedPreferences(context: Context): SharedPreferences {
            return context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        }
    }
}