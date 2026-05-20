package com.wearabouts.lite.data.local

import android.content.Context
import android.content.SharedPreferences

class UserPreferences(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    fun saveUserName(name: String) {
        sharedPreferences.edit().putString("user_name", name).apply()
    }

    fun getUserName(): String {
        return sharedPreferences.getString("user_name", "Guest") ?: "Guest"
    }

    fun saveProfilePictureUri(uri: String?) {
        sharedPreferences.edit().putString("profile_picture_uri", uri).apply()
    }

    fun getProfilePictureUri(): String? {
        return sharedPreferences.getString("profile_picture_uri", null)
    }

    fun setPrivateMode(enabled: Boolean) {
        sharedPreferences.edit().putBoolean("private_mode", enabled).apply()
    }

    fun isPrivateModeEnabled(): Boolean {
        return sharedPreferences.getBoolean("private_mode", false)
    }
    
    fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}
