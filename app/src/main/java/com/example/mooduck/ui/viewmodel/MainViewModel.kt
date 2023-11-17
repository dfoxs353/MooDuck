package com.example.mooduck.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.mooduck.common.RetrofitClient
import com.example.mooduck.data.repository.LocalUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    fun setRetrofitToken(accessToken: String, refreshToken:String){

        if (accessToken == null || refreshToken == null) {
            return
        }
        Log.d("log", "token :${accessToken} and ${refreshToken}")
    }


}