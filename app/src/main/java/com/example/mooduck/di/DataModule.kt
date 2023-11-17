package com.example.mooduck.di

import com.example.mooduck.common.RetrofitClient
import com.example.mooduck.data.remote.auth.AuthApi
import com.example.mooduck.data.repository.RemoteAuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideRemoteAuthRepository(): RemoteAuthRepository {
        val retrofit = RetrofitClient.instance
        val userApi = retrofit.create(AuthApi::class.java)

        return RemoteAuthRepository(userApi, Dispatchers.IO)
    }
}