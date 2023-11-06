package com.example.mooduck.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.mooduck.common.RetrofitClient
import com.example.mooduck.data.repository.LocalUserRepository

class MainViewModel(context: Context) : ViewModel() {


    private val _retrofit = RetrofitClient
    private val localUserRepository: LocalUserRepository = LocalUserRepository(context)

    fun setRetrofitToken(){
        val accessToken = getAccessToken()
        val refreshToken = getRefreshToken()
        if (accessToken == null || refreshToken == null) {
            return
        }
        _retrofit.setTokens(accessToken,refreshToken)
        Log.d("log", "token :${accessToken} and ${refreshToken}")
    }

    private fun getAccessToken(): String? {
        return localUserRepository.getAccessToken()
    }

    private fun getRefreshToken(): String? {
        return localUserRepository.getRefreshToken()
    }
}