package com.example.plm

import android.content.Context
import android.content.SharedPreferences


class SharedPreferencesManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    companion object {
        private const val PREF_NAME = "PLM"
        private const val USER_CODE = "codeUser"
    }

    fun saveUser(value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(USER_CODE, value)
        editor.apply()
    }

    fun getUser(): String? {
        return sharedPreferences.getString(USER_CODE, null)
    }
}