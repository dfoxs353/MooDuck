package com.example.mooduck.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mooduck.R
import com.example.mooduck.data.remote.Result
import com.example.mooduck.data.remote.books.CertainBookResult
import com.example.mooduck.data.repository.BooksRepository
import com.example.mooduck.data.repository.RemoteUserRepository
import com.example.mooduck.ui.model.BookState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookPageViewModel @Inject constructor(
    private val remoteBookRepository: BooksRepository,
    private val remoteUserRepository: RemoteUserRepository,
) : ViewModel() {
    private val _bookResult = MutableLiveData<CertainBookResult>()
    val bookResult : LiveData<CertainBookResult> = _bookResult

    private val _bookState = MutableLiveData<BookState>()
    val bookState: LiveData<BookState> = _bookState


    suspend fun getBook(id: String){
        val result = remoteBookRepository.getBook(id)

        if(result is Result.Success){
            _bookResult.value = CertainBookResult(success = result.data)
        }
        else{
            _bookResult.value = CertainBookResult(error = R.string.error_loading)
        }
    }

    suspend fun getFavoriteBook(id: String){
        val result = remoteUserRepository.
    }

    suspend fun setToReadBook(id: String, userId: String){
        val result = remoteUserRepository.setFavouriteBook(id, userId)

        if (result is Result.Success){
            _bookState.value!!.bookToRead = result.data
        }
        else{

        }
    }
}