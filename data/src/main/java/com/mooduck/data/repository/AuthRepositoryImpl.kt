package com.mooduck.data.repository

import android.util.Log
import com.mooduck.data.mappers.toAuthUser
import com.mooduck.data.remote.auth.AuthApi
import com.mooduck.domain.models.Result
import com.mooduck.data.remote.auth.UserLoginRequest
import com.mooduck.data.remote.auth.UserRegistrationRequest
import com.mooduck.domain.models.AuthUser
import com.mooduck.domain.repository.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
    private val userDataSource: AuthApi,
    private val ioDispatcher: CoroutineDispatcher
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<AuthUser> {
        try {
            return Result.Success(
                withContext(ioDispatcher) {
                    val response = userDataSource.loginUser(UserLoginRequest(email, password))
                    response.await()
                }.toAuthUser()
            )
        } catch (e: Exception) {
            Log.d("TAG", e.message.toString())
            return Result.Error(IOException("Error logging in", e))
        }
    }

    override suspend fun signup(email: String, password: String, username: String): Result<AuthUser> {
        try {
            return Result.Success(
                withContext(ioDispatcher) {
                    val response = userDataSource.registerUser(UserRegistrationRequest(email, password, username))
                    response.await()
                }.toAuthUser()
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
                }.toAuthUser().refreshToken
            )
        } catch (e: Exception) {
            Log.d("TAG", e.message.toString())
            return Result.Error(IOException("Error refresh", e))
        }
    }
}