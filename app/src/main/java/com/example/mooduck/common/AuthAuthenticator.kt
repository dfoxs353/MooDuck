package com.example.mooduck.common

import com.mooduck.data.remote.auth.AuthApi
import com.mooduck.data.remote.auth.AuthResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.mooduck.domain.repository.LocalUserRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
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
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(
    private val tokenManager: LocalUserRepository,
): Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val refreshToken = tokenManager.getRefreshToken()

        if (refreshToken != null) {
            val newAccessToken = tokenManager.getAccessToken()
            if (response.request.header("Authorization") == "Bearer $newAccessToken") {
                return response.request.newBuilder()
                    .header("Authorization", "Bearer $newAccessToken")
                    .build()
            }

            return runBlocking {
                val newToken = getNewToken(refreshToken)
                if (newToken != null) {
                    response.request.newBuilder()
                        .header("Authorization", "Bearer $newToken")
                        .build()
                }

                tokenManager.clearUser()
                null
            }
        }
        return null
    }
    private suspend fun getNewToken(refreshToken: String): String? {
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

        return try {
            withContext(Dispatchers.IO){
                val result = service.refreshToken(refreshToken)
                result.await()
            }.accessToken
        }
        catch (e: Exception){
            null
        }
    }
}