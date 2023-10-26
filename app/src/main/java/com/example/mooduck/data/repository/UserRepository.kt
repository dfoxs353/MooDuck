package com.example.mooduck.data.repository

import android.util.Log
import com.example.mooduck.MooDuckApp
import com.example.mooduck.data.remote.auth.AuthApi
import com.example.mooduck.data.remote.auth.AuthResponse
import com.example.mooduck.data.remote.auth.User
import com.example.mooduck.data.remote.auth.UserLoginRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.withContext

class UserRepository(
    private val userApi: AuthApi,
    private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun login(email: String, password: String): AuthResponse? {
        try {
            return withContext(ioDispatcher) {
                val response = userApi.loginUser(UserLoginRequest(email, password))
                response.await()
            }
        } catch (e: Exception) {
            Log.d("TAG", e.message.toString())
        }
        return null
    }


}