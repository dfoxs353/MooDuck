package com.example.mooduck

import android.app.Application
import com.mooduck.data.remote.auth.AuthApi
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@HiltAndroidApp
class MooDuckApp: Application()