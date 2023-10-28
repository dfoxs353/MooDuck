package com.example.mooduck.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.mooduck.common.RetrofitClient
import com.example.mooduck.data.remote.auth.AuthApi
import com.example.mooduck.data.remote.auth.AuthResponse
import com.example.mooduck.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers

class SignUpViewModel: ViewModel() {

    private val retrofit = RetrofitClient.instance
    private val userApi = retrofit.create(AuthApi::class.java)

    private val userRepository: UserRepository = UserRepository(userApi, Dispatchers.IO)

//    suspend fun signUp(email: String, password: String, username: String): AuthResponse? {
//        val response = userRepository.signup(email, password, username)
//
//        if(response !=null){
//            Log.d("TAG", "user ${response.user.username}")
//            return response
//        }
//
//        return null
//    }
}