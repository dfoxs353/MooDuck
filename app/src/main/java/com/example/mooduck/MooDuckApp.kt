package com.example.mooduck

import android.app.Application
import com.example.mooduck.data.remote.auth.AuthApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MooDuckApp: Application() {

    override fun onCreate() {
        super.onCreate()

        configureRetrofit()
    }

    private fun configureRetrofit() {

    }
}