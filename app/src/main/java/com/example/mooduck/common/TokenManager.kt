package com.example.mooduck.common

class TokenManager {
    private var accessToken: String? = null

    fun setAccessToken(accessToken: String) {
        this.accessToken = accessToken
    }

    fun getAccessToken(): String? {
        return accessToken
    }


}