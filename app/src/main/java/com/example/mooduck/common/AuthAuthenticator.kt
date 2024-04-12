package com.example.mooduck.common

import com.mooduck.data.remote.auth.AuthApi
import com.mooduck.data.remote.auth.AuthResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.mooduck.domain.repository.UserRepository
import kotlinx.coroutines.runBlocking
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
    private val tokenManager: UserRepository,
): Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val user = runBlocking {
            tokenManager.getUser()
        }
        return runBlocking {
            val newToken = getNewToken(user?.refreshToken)
            if (!newToken.isSuccessful  || newToken.body() == null) {
                tokenManager.clearUser()
            }
            newToken.body()?.let {
                tokenManager.saveUser(
                    user!!.copy(
                        refreshToken = it.refreshToken,
                        accessToken = it.accessToken,
                    )
                )
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