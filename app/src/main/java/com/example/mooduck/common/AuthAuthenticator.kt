package com.example.mooduck.common

import android.util.Log
import com.example.mooduck.data.remote.Result
import com.example.mooduck.data.remote.auth.AuthApi
import com.example.mooduck.data.remote.auth.AuthResponse
import com.example.mooduck.data.repository.LocalUserRepository
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(
    private val tokenManager: LocalUserRepository,
): Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val token = runBlocking {
            tokenManager.getRefreshToken()
        }
        return runBlocking {
            val newToken = getNewToken(token)
            if (!newToken.isSuccessful  || newToken.body() == null) { //Couldn't refresh the token, so restart the login process
                tokenManager.clearUserData()
            }
            newToken.body()?.let {
                tokenManager.saveJWToken(it.refreshToken,it.accessToken)
                response.request.newBuilder()
                    .header("Authorization", "Bearer ${it.accessToken}")
                    .build()
            }
        }
    }
    private suspend fun getNewToken(refreshToken: String?): retrofit2.Response<AuthResponse> {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://mooduck-service-api.onrender.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
            .build()
        val service = retrofit.create(AuthApi::class.java)
        return service.refreshToken("Bearer $refreshToken")
    }
}