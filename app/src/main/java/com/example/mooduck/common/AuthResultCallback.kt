package com.example.mooduck.common

interface AuthResultCallback {
    fun onAuthSuccess(accessToken: String)
    fun onAuthFailure()
}
