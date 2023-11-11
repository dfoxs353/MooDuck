package com.example.mooduck.data.repository

import android.content.Context
import android.content.SharedPreferences

class LocalUserRepository(context: Context) {
    private val PREFS_NAME = "user_prefs"

    private val KEY_ACCESS_TOKEN = "access_token"
    private val KEY_REFRESH_TOKEN = "refresh_token"
    private val KEY_USER_ID = "user_id"
    private val KEY_USER_PASSWORD = "user_password"

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun saveUser(userID: String, passsword: String, refreshToken: String, accessToken: String){
        editor.putString(KEY_USER_ID,userID)
        editor.putString(KEY_USER_PASSWORD,passsword)
        editor.putString(KEY_REFRESH_TOKEN, refreshToken)
        editor.putString(KEY_ACCESS_TOKEN, accessToken)
        editor.apply()
    }

    fun getUserId(): String?{
        return sharedPreferences.getString(KEY_USER_ID, null)
    }

    fun getUserPassword(): String?{
        return sharedPreferences.getString(KEY_USER_PASSWORD, null)
    }

    fun saveJWToken(refreshToken: String, accessToken: String) {
        editor.putString(KEY_REFRESH_TOKEN, refreshToken)
        editor.putString(KEY_ACCESS_TOKEN, accessToken)
        editor.apply()
    }

    fun getRefreshToken(): String? {
        return sharedPreferences.getString(KEY_REFRESH_TOKEN, null)
    }

    fun getAccessToken(): String? {
        return sharedPreferences.getString(KEY_ACCESS_TOKEN, null)
    }

    fun clearUserData() {
        editor.remove(KEY_REFRESH_TOKEN)
        editor.remove(KEY_ACCESS_TOKEN)
        editor.remove(KEY_USER_ID)
        editor.remove(KEY_USER_PASSWORD)
        editor.apply()
    }


}