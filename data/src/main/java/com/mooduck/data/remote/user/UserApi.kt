package com.mooduck.data.remote.user

import com.mooduck.data.remote.auth.User
import com.mooduck.data.remote.books.BooksResponse
import com.mooduck.data.remote.books.CommentResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

data class ChangeFavoriteRequest(
    val bookId: String
)

interface UserApi {


    @POST("users/{userId}/favoritebooks")
    suspend fun addBookToFavorite(
        @Path("userId") userId: String,
        @Body request: ChangeFavoriteRequest
    ): Response<Boolean>

    @HTTP(method = "DELETE", path = "users/{userId}/favoritebooks", hasBody = true)
    suspend fun deleteBookFromFavorite(
        @Path("userId") userId: String,
        @Body bookId: ChangeFavoriteRequest
    ): Response<Boolean>
    @GET("users/{userId}/favoritebooks")
    fun getFavoriteBooks(
        @Path("userId") userId: String,
        @Query("limit") limit: Int? = null,
        @Query("page") page: Int? = null,
    ):Deferred<BooksResponse>

    @GET("/users/{id}")
    fun getUser(@Path("id") id: String): Deferred<User>

    @GET("/users/{id}/comments")
    fun getUserComments(@Path("id") id: String): Deferred<List<CommentResponse>>

    @PUT("/users/{id}/username")
    fun changeUserUsername(@Path("id") id: String, @Body data: ChangeUserData): Deferred<Any>

    @PUT("/users/{id}/email")
    fun changeUserEmail(@Path("id") id: String, @Body data: ChangeUserData): Deferred<Any>

    @POST("/users/{id}/checkpassword")
    fun checkUserPassword(@Path("id") id: String, @Body data: ChangeUserData): Deferred<Any>

    @PUT("/users/{id}/password")
    fun changeUserPassword(@Path("id") id: String, @Body data: ChangeUserData): Deferred<Any>

    @POST("/auth/resetPassword")
    fun resetPassword(@Body email: String): Deferred<Any>

    @PUT("/auth/resetPassword")
    fun changeResetPassword(@Body data: ChangeResetPasswordData): Deferred<String>
}