package com.example.mooduck.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mooduck.R
import com.example.mooduck.common.RetrofitClient
import com.example.mooduck.data.remote.Result
import com.example.mooduck.data.remote.auth.AuthApi
import com.example.mooduck.data.remote.books.BookApi
import com.example.mooduck.data.remote.books.BookResult
import com.example.mooduck.data.remote.books.BooksResult
import com.example.mooduck.data.remote.books.CertainBookResponse
import com.example.mooduck.data.remote.books.CertainBookResult
import com.example.mooduck.data.repository.BooksRepository
import com.example.mooduck.data.repository.LocalUserRepository
import kotlinx.coroutines.Dispatchers

class BookPageViewModel : ViewModel() {
    private val _bookResult = MutableLiveData<CertainBookResult>()
    val bookResult : LiveData<CertainBookResult> = _bookResult

    private val retrofit = RetrofitClient.instance
    private val bookAPi = retrofit.create(BookApi::class.java)

    private val remoteBookRepository: BooksRepository = BooksRepository(bookAPi, Dispatchers.IO)

    suspend fun getBook(id: String){
        val result = remoteBookRepository.getBook(id)

        if(result is Result.Success){
            _bookResult.value = CertainBookResult(success = result.data)
        }
        else{
            _bookResult.value = CertainBookResult(error = R.string.error_loading)
        }
    }
}