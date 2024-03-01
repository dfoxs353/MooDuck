package com.example.mooduck.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mooduck.R
import com.mooduck.domain.models.Result
import com.example.mooduck.data.remote.books.BooksResult
import com.example.mooduck.data.repository.BooksRepository
import com.example.mooduck.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookListViewModel @Inject constructor(
    private val booksRepository: BooksRepository,
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _booksResult = MutableLiveData<BooksResult>()
    val booksResult: LiveData<BooksResult> = _booksResult

    suspend fun getBooks(limit: Int?=null,page: Int?=null, genre: String?=null, author: String?=null){
        val result = booksRepository.getBooks(limit, page, genre, author)

        if (result is com.mooduck.domain.models.Result.Success){
            _booksResult.value = BooksResult(success = result.data)
        }
        else{
            _booksResult.value = BooksResult(error = R.string.error_loading)
        }
    }

    suspend fun setFavouriteBook(booId: String, userId: String){
        val result = userRepository.setFavouriteBook(booId,userId)

        if (result is com.mooduck.domain.models.Result.Success){
            //TODO//
        }
        else{
            //TODO//
        }
    }
}