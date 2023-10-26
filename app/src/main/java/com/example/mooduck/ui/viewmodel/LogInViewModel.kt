package com.example.mooduck.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.mooduck.common.RetrofitClient
import com.example.mooduck.data.remote.auth.AuthApi
import com.example.mooduck.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import okhttp3.Dispatcher

class LogInViewModel : ViewModel() {

    private val retrofit = RetrofitClient.instance
    private val userApi = retrofit.create(AuthApi::class.java)

    private val userRepository: UserRepository = UserRepository(userApi, Dispatchers.IO)

    suspend fun login(email: String, password: String):Boolean{
        val response = userRepository.login(email, password)

        if(response !=null) return true
        return false
    }
}