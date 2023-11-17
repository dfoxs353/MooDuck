package com.example.mooduck.di

import android.content.Context
import com.example.mooduck.common.RetrofitClient
import com.example.mooduck.data.remote.auth.AuthApi
import com.example.mooduck.data.remote.books.BookApi
import com.example.mooduck.data.remote.user.UserApi
import com.example.mooduck.data.repository.BooksRepository
import com.example.mooduck.data.repository.LocalUserRepository
import com.example.mooduck.data.repository.RemoteAuthRepository
import com.example.mooduck.data.repository.RemoteUserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    @Singleton
    fun provideLocalUserRepository(@ApplicationContext context: Context): LocalUserRepository{
        return LocalUserRepository(context = context)
    }

    @Provides
    @Singleton
    fun provideRemoteBookRepository(): BooksRepository{
        val retrofit = RetrofitClient.instance
        val bookApi = retrofit.create(BookApi::class.java)

        return BooksRepository(bookApi,Dispatchers.IO)
    }

    @Provides
    @Singleton
    fun provideRemoteUserRepository(): RemoteUserRepository{
        val retrofit = RetrofitClient.instance
        val userApi = retrofit.create(UserApi::class.java)

        return RemoteUserRepository(userApi,Dispatchers.IO)
    }
}