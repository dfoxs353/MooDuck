package com.example.mooduck.data.remote.user

import com.example.mooduck.data.remote.Result
import com.example.mooduck.data.remote.auth.User
import com.example.mooduck.data.remote.books.BooksResponse
import com.example.mooduck.data.remote.books.Comment
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApi {

    @POST("users/{userId}/favoritebooks")
    fun addBookToFavorite(
        @Body bookId: String,
        @Path("userId") userId: String,
    ):Deferred<Result<Boolean>>

    @DELETE("users/{userId}/favoritebooks")
    suspend fun deleteBookFromFavorite(
        @Body bookId: String,
        @Path("userId") userId: String,
    ): Deferred<Result<Any>>

    @GET("users/{userId}/favoritebooks")
    fun getFavoriteBooks(
        @Body bookId: String,
        @Path("userId") userId: String,
    ):Deferred<Result<Any>>

    @GET("/users/{id}")
    fun getUser(@Path("id") id: String): User

    @GET("/users/{id}/favoritebooks")
    fun getUserFavoriteBooks(@Path("id") id: String, @Query("limit") limit: Int, @Query("page") page: Int): Deferred<BooksResponse>

    @GET("/users/{id}/comments")
    fun getUserComments(@Path("id") id: String): Deferred<List<Comment>>

    @PUT("/users/{id}/username")
    fun changeUserUsername(@Path("id") id: String, @Body data: ChangeUserData): Deferred<Result<Any>>

    @PUT("/users/{id}/email")
    fun changeUserEmail(@Path("id") id: String, @Body data: ChangeUserData): Deferred<Result<Any>>

    @POST("/users/{id}/checkpassword")
    fun checkUserPassword(@Path("id") id: String, @Body data: ChangeUserData): Deferred<Result<Any>>

    @PUT("/users/{id}/password")
    fun changeUserPassword(@Path("id") id: String, @Body data: ChangeUserData): Deferred<Result<Any>>

    @POST("/auth/resetPassword")
    fun resetPassword(@Body email: String): Deferred<Result<Any>>

    @PUT("/auth/resetPassword")
    fun changeResetPassword(@Body data: ChangeResetPasswordData): Deferred<Result<String>>
}