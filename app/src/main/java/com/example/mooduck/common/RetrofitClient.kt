package com.example.mooduck.common

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private var BASE_URL = "https://mooduck-service-api.onrender.com/api/"
    private val tokenInterceptor = TokenInterceptor(null)
    private val cookieInterceptor = CookieInterceptor(null)

    private var originalInstance: Retrofit? = null

    val instance: Retrofit
        get() {
            if (originalInstance == null) {
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

                val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(httpLoggingInterceptor)
                    .addInterceptor(tokenInterceptor)
                    .addInterceptor(cookieInterceptor)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
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

    fun setBaseUrl(newBaseUrl: String) {
        BASE_URL = newBaseUrl
        originalInstance = null
    }

    fun setTokens(accessToken:String, refreshToken: String){
        setAccessToken(accessToken)
        setRefreshToken(refreshToken)
    }

    fun setAccessToken(token: String?) {
        tokenInterceptor.setToken(token)
        originalInstance = null
    }

    fun setRefreshToken(token: String){
        cookieInterceptor.setToken(token)
        originalInstance = null
    }

}