package com.mooduck.data.repository

import android.util.Log
import com.mooduck.data.mappers.toUser
import com.mooduck.data.remote.auth.AuthApi
import com.mooduck.data.remote.auth.AuthResponse
import com.mooduck.domain.models.Result
import com.mooduck.data.remote.auth.UserLoginRequest
import com.mooduck.data.remote.auth.UserRegistrationRequest
import com.mooduck.domain.models.User
import com.mooduck.domain.repository.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.IOException


class AuthRepositoryIml(
    private val userDataSource: AuthApi,
    private val ioDispatcher: CoroutineDispatcher
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<User> {
        try {
            return Result.Success(
                withContext(ioDispatcher) {
                    val response = userDataSource.loginUser(UserLoginRequest(email, password))
                    response.await()
                }.toUser()
            )
        } catch (e: Exception) {
            Log.d("TAG", e.message.toString())
            return Result.Error(IOException("Error logging in", e))
        }
    }

    override suspend fun signup(email: String, password: String, username: String): Result<User> {
        try {
            return Result.Success(
                withContext(ioDispatcher) {
                    val response = userDataSource.registerUser(UserRegistrationRequest(email, password, username))
                    response.await()
                }.toUser()
            )
        } catch (e: Exception) {
            Log.d("TAG", e.message.toString())
            return Result.Error(IOException("Error signup in", e))
        }

    }

    override suspend fun refresh(token: String): Result<String> {
        try {
            return Result.Success(
                withContext(ioDispatcher) {
                    val response = userDataSource.refresh(token)
                    response.await()
                }.toUser().refreshToken
            )
        } catch (e: Exception) {
            Log.d("TAG", e.message.toString())
            return Result.Error(IOException("Error refresh", e))
        }
    }
}