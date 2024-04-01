package com.example.mooduck.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mooduck.R
import com.mooduck.data.remote.books.CertainBookResult
import com.mooduck.data.repository.BooksRepositoryImpl
import com.mooduck.data.repository.UserRepositoryImpl
import com.example.mooduck.ui.model.BookState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookPageViewModel @Inject constructor(
    private val remoteBookRepository: BooksRepositoryImpl,
    private val userRepository: UserRepositoryImpl,
) : ViewModel() {
    private val _bookResult = MutableLiveData<CertainBookResult>()
    val bookResult : LiveData<CertainBookResult> = _bookResult

    private val _bookState = MutableLiveData<BookState>()
    val bookState: LiveData<BookState> = _bookState


    suspend fun getBook(id: String){
        val result = remoteBookRepository.getBook(id)

        if(result is com.mooduck.domain.models.Result.Success){
            _bookResult.value = CertainBookResult(success = result.data)
        }
        else{
            _bookResult.value = CertainBookResult(error = R.string.error_loading)
        }
    }

    suspend fun checkFavoriteBook(userId: String, bookId: String){
        val result = userRepository.getFavouriteBooks(userId)

        if (result is com.mooduck.domain.models.Result.Success){
            result.data.books.map {
                if (it._id == bookId){
                    _bookState.value!!.bookToRead = true
                    return
                }
            }
        }
    }

    suspend fun setToReadBook(id: String, userId: String){
        val result = userRepository.setFavouriteBook(id, userId)

        if (result is com.mooduck.domain.models.Result.Success){
            _bookState.value!!.bookToRead = result.data
        }
        else{

        }
    }
}