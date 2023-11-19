package com.example.mooduck.di

import android.content.Context
import android.content.SharedPreferences
import com.example.mooduck.common.AuthAuthenticator
import com.example.mooduck.common.AuthInterceptor
import com.example.mooduck.data.remote.auth.AuthApi
import com.example.mooduck.data.remote.books.BookApi
import com.example.mooduck.data.remote.user.UserApi
import com.example.mooduck.data.repository.BooksRepository
import com.example.mooduck.data.repository.LocalUserRepository
import com.example.mooduck.data.repository.RemoteAuthRepository
import com.example.mooduck.data.repository.RemoteUserRepository
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideLocalUserRepository(sharedPreferences: SharedPreferences): LocalUserRepository{
        return LocalUserRepository(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        val PREFS_NAME = "user_prefs"
        return context.getSharedPreferences(context.packageName + PREFS_NAME, Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideAuthInterceptor(tokenManager: LocalUserRepository): AuthInterceptor =
        AuthInterceptor(tokenManager)

    @Singleton
    @Provides
    fun provideAuthAuthenticator(tokenManager: LocalUserRepository): AuthAuthenticator =
        AuthAuthenticator(tokenManager)

    @Singleton
    @Provides
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        authAuthenticator: AuthAuthenticator,
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .authenticator(authAuthenticator)
            .build()
    }


    @Singleton
    @Provides
    fun provideRetrofitBuilder(): Retrofit.Builder =
        Retrofit.Builder()
            .baseUrl("https://mooduck-service-api.onrender.com/api/")
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())

    @Singleton
    @Provides
    fun provideAuthAPIService(retrofit: Retrofit.Builder): AuthApi =
        retrofit
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .build()
            )
            .build()
            .create(AuthApi::class.java)

    @Singleton
    @Provides
    fun provideBookAPIService(okHttpClient: OkHttpClient, retrofit: Retrofit.Builder): BookApi =
        retrofit
            .client(okHttpClient)
            .build()
            .create(BookApi::class.java)

    @Singleton
    @Provides
    fun provideUserAPIService(okHttpClient: OkHttpClient, retrofit: Retrofit.Builder): UserApi =
        retrofit
            .client(okHttpClient)
            .build()
            .create(UserApi::class.java)

    @Provides
    @Singleton
    fun provideRemoteAuthRepository(authApi: AuthApi): RemoteAuthRepository {
        return RemoteAuthRepository(authApi, Dispatchers.IO)
    }

    @Provides
    @Singleton
    fun provideRemoteUserRepository(userApi: UserApi): RemoteUserRepository{
        return RemoteUserRepository(userApi,Dispatchers.IO)
    }

    @Provides
    @Singleton
    fun provideRemoteBookRepository(bookApi: BookApi): BooksRepository{
        return BooksRepository(bookApi,Dispatchers.IO)
    }


}