package com.example.mooduck.data.repository

import android.util.JsonToken
import android.util.Log
import com.example.mooduck.data.remote.auth.AuthApi
import com.example.mooduck.data.remote.auth.AuthResponse
import com.example.mooduck.data.remote.Result
import com.example.mooduck.data.remote.auth.UserLoginRequest
import com.example.mooduck.data.remote.auth.UserRegistrationRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.IOException


class RemoteAuthRepository(
    private val userDataSource: AuthApi,
    private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun login(email: String, password: String): Result<AuthResponse> {
        try {
            return Result.Success(
                withContext(ioDispatcher) {
                    val response = userDataSource.loginUser(UserLoginRequest(email, password))
                    response.await()
                }
            )
        } catch (e: Exception) {
            Log.d("TAG", e.message.toString())
            return Result.Error(IOException("Error logging in", e))
        }
    }

    suspend fun signup(email: String, password: String, username: String): Result<AuthResponse> {
        try {
            return Result.Success(
                withContext(ioDispatcher) {
                    val response = userDataSource.registerUser(UserRegistrationRequest(email, password, username))
                    response.await()
                }
            )
        } catch (e: Exception) {
            Log.d("TAG", e.message.toString())
            return Result.Error(IOException("Error signup in", e))
        }

    }

    suspend fun refresh(token: String): Result<AuthResponse>{
        try {
            return Result.Success(
                withContext(ioDispatcher) {
                    val response = userDataSource.refresh(token)
                    response.await()
                }
            )
        } catch (e: Exception) {
            Log.d("TAG", e.message.toString())
            return Result.Error(IOException("Error refresh", e))
        }
    }
}