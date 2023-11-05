package com.example.mooduck.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mooduck.R
import com.example.mooduck.common.RetrofitClient
import com.example.mooduck.data.remote.Result
import com.example.mooduck.data.remote.books.BookApi
import com.example.mooduck.data.remote.books.BooksResult
import com.example.mooduck.data.repository.BooksRepository
import kotlinx.coroutines.Dispatchers

class BookListViewModel : ViewModel() {

    private val _booksResult = MutableLiveData<BooksResult>()
    val booksResult: LiveData<BooksResult> = _booksResult

    private val retrofit = RetrofitClient.instance
    private val bookApi = retrofit.create(BookApi::class.java)

    private val booksRepository: BooksRepository = BooksRepository(bookApi, Dispatchers.IO)

    suspend fun getBooks(limit: Int?=null,page: Int?=null, genre: String?=null, author: String?=null){
        val result = booksRepository.getBooks(limit, page, genre, author)

        if (result is Result.Success){
            _booksResult.value = BooksResult(success = result.data)
        }
        else{
            _booksResult.value = BooksResult(error = R.string.error_loading)
        }
    }
}