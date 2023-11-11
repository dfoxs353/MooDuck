package com.example.mooduck.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.mooduck.common.RetrofitClient
import com.example.mooduck.data.repository.LocalUserRepository

class MainViewModel() : ViewModel() {


    private val _retrofit = RetrofitClient

    fun setRetrofitToken(accessToken: String, refreshToken:String){

        if (accessToken == null || refreshToken == null) {
            return
        }
        Log.d("log", "token :${accessToken} and ${refreshToken}")
    }


}