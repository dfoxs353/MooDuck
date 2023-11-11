package com.example.mooduck.common

import android.util.Log
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private var BASE_URL = "https://mooduck-service-api.onrender.com/api/"

    private var originalInstance: Retrofit? = null

    private val tokenManager = TokenManager()
    private val cookieManager = CookieManager()

    val instance: Retrofit
        get() {
            if (originalInstance == null) {
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

                val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(httpLoggingInterceptor)
                    .addInterceptor { chain ->
                        val request = chain.request().newBuilder()
                        tokenManager.getAccessToken()?.let { token ->
                            request.addHeader("Authorization", "Bearer $token")
                        }
                        val newRequest = request.build()
                        chain.proceed(newRequest)
                    }
                    .cookieJar(cookieManager)
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .build()

                originalInstance = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .build()
            }
            return originalInstance!!
        }

    fun setAccessAndRefreshTokens(accessToken: String, refreshToken: String) {
        tokenManager.setAccessToken(accessToken)
        cookieManager.setRefreshToken(refreshToken)
        originalInstance = null
    }
}