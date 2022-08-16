package com.daily.app.common

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_APPEND
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences


class AppPreferences {
    companion object {
        @SuppressLint("CommitPrefEdits")
        fun setDefaultPreference(context: Context, value: String) {
            saveAppPref(context, value, Constants.PREF_KEY_DEFAULT_CONFIG)
        }

        fun getDefaultPreference(context: Context) : String?{
            return getAppPref(context, Constants.PREF_KEY_DEFAULT_CONFIG)
        }

        private fun saveAppPref(context: Context, value: String, key: String) {
            val sharedPreferences: SharedPreferences = context.getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString(key, value)
            editor.apply()
        }

        private fun getAppPref(context: Context, key: String) : String? {
            val sharedPreferences: SharedPreferences = context.getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE)
            return sharedPreferences.getString(key, "")
        }
    }
}