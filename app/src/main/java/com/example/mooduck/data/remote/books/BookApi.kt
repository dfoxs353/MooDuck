package com.example.mooduck.data.remote.books

import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path

interface BookApi {

    @GET("books")
    fun getBooks(@Body request: BooksRequest):Deferred<BooksResponse>


    @GET("books/{id}")
    fun getBook(@Path("id") id: String):Deferred<BookResponse>

    @GET("books/{id}/comments")
    fun getBookComments(@Path("id") id: String):Deferred<List<Comment>>
}