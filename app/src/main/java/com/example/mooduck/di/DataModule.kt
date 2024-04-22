package com.example.mooduck.di

import android.content.Context
import android.content.SharedPreferences
import androidx.media3.common.BuildConfig
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.example.mooduck.common.AuthAuthenticator
import com.example.mooduck.common.AuthInterceptor
import com.mooduck.data.remote.auth.AuthApi
import com.mooduck.data.remote.books.BookApi
import com.mooduck.data.remote.user.UserApi
import com.mooduck.data.repository.BooksRepositoryImpl
import com.mooduck.data.repository.AuthRepositoryImpl
import com.mooduck.data.repository.LocalUserRepositoryImpl
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.mooduck.data.local.BookDatabase
import com.mooduck.data.local.LocalUserDataSource
import com.mooduck.data.local.models.BookEntity
import com.mooduck.data.remote.BookRemoteMediator
import com.mooduck.data.repository.RemoteUserRepositoryImpl
import com.mooduck.domain.repository.AuthRepository
import com.mooduck.domain.repository.BooksRepository
import com.mooduck.domain.repository.LocalUserRepository
import com.mooduck.domain.repository.RemoteUserRepository
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

@OptIn(ExperimentalPagingApi::class)
@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideBookDataBase(@ApplicationContext context: Context): BookDatabase{
        return Room.databaseBuilder(
            context,
            BookDatabase::class.java,
            "books.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideBookPager(bookDb: BookDatabase, bookApi: BookApi): Pager<Int, BookEntity>{
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = BookRemoteMediator(
                bookDb = bookDb,
                bookApi = bookApi,
                ioDispatcher = Dispatchers.IO
            ),
            pagingSourceFactory = {
                bookDb.dao.pagingSource()
            }
        )
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
    fun provideAuthAPIService(retrofit: Retrofit.Builder): AuthApi
    {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return retrofit
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .build()
            )
            .build()
            .create(AuthApi::class.java)
    }

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
    fun provideAuthRepository(authApi: AuthApi): AuthRepository {
        return AuthRepositoryImpl(authApi, Dispatchers.IO)
    }

    @Provides
    @Singleton
    fun provideLocalUserDataSource(sharedPreferences: SharedPreferences): LocalUserDataSource{
        return LocalUserDataSource(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideRemoteBookRepository(bookApi: BookApi): BooksRepository {
        return BooksRepositoryImpl(bookApi,Dispatchers.IO)
    }


    @Provides
    @Singleton
    fun provideLocalUserRepository(localUserDataSource: LocalUserDataSource): LocalUserRepository{
        return LocalUserRepositoryImpl(localUserDataSource)
    }

    @Provides
    @Singleton
    fun provideRemoteUserRepository(userApi: UserApi): RemoteUserRepository{
        return RemoteUserRepositoryImpl(userApi,Dispatchers.IO)
    }
}