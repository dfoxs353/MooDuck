package com.example.mooduck.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mooduck.common.RetrofitClient
import com.example.mooduck.data.remote.books.BookApi
import com.example.mooduck.data.repository.BooksRepository
import kotlinx.coroutines.Dispatchers

class BookViewModel : ViewModel() {
    private val retrofit = RetrofitClient.instance
    private val bookAPi = retrofit.create(BookApi::class.java)

    private val remoteBookRepository: BooksRepository = BooksRepository(bookAPi, Dispatchers.IO)

    suspend fun setToReadBook(id:String){

    }
}