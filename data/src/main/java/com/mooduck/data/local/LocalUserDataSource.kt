package com.mooduck.data.local

import android.content.SharedPreferences
import com.mooduck.domain.models.User
import javax.inject.Inject

class LocalUserDataSource(
    private val sharedPreferences: SharedPreferences,
) {


    private val KEY_ACCESS_TOKEN = "access_token"
    private val KEY_USER_NAME = "user_name"
    private val KEY_REFRESH_TOKEN = "refresh_token"
    private val KEY_USER_ID = "user_id"
    private val KEY_USER_PASSWORD = "user_password"

    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun saveUser(user: User) {
        with(user){
            editor.putString(KEY_USER_ID, userid)
            editor.putString(KEY_USER_PASSWORD, userPassword)
            editor.putString(KEY_REFRESH_TOKEN, refreshToken)
            editor.putString(KEY_ACCESS_TOKEN, accessToken)
            editor.putString(KEY_USER_NAME, userName)
            editor.apply()
        }
    }

    fun getUser(): User? {
        val userId = getUserId()
        val userName = getUserName()
        val accessToken = getAccessToken()
        val refreshToken = getRefreshToken()

        return if (userId.isNullOrEmpty() || accessToken.isNullOrEmpty()
            || refreshToken.isNullOrEmpty() || userName.isNullOrEmpty()) {
            null
        } else User(
            userid = userId,
            accessToken = accessToken,
            userName = userName,
            refreshToken = refreshToken,
        )
    }

    fun saveJWToken(accessToken: String, refreshToken: String, ) {
        editor.putString(KEY_REFRESH_TOKEN, refreshToken)
        editor.putString(KEY_ACCESS_TOKEN, accessToken)
        editor.apply()
    }


    fun getUserId(): String? {
        return sharedPreferences.getString(KEY_USER_ID, null)
    }

    fun getUserName(): String?{
        return sharedPreferences.getString(KEY_USER_NAME, null)
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
        editor.remove(KEY_USER_NAME)
        editor.remove(KEY_USER_ID)
        editor.remove(KEY_USER_PASSWORD)
        editor.apply()
    }
}