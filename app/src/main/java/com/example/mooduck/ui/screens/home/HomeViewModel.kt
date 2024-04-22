package com.example.mooduck.ui.screens.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.mooduck.ui.models.BookUI
import com.example.mooduck.ui.screens.home.models.HomeSubState
import com.example.mooduck.ui.screens.home.models.HomeViewState
import com.mooduck.data.local.models.BookEntity
import com.mooduck.data.mappers.toBook
import com.mooduck.domain.models.Result
import com.mooduck.domain.repository.BooksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val booksRepository: BooksRepository,
    pager: Pager<Int, BookEntity>
) : ViewModel(){

    private val _viewState = MutableLiveData(HomeViewState())
    val viewState: LiveData<HomeViewState> = _viewState

    val bookPagingFlow = pager
        .flow
        .map { pagingData ->
            pagingData.map {
                it.toBook()
            }
        }
        .cachedIn(viewModelScope)

    suspend fun getBooks(limit: Int?=100,page: Int?=1, genre: String?=null, author: String?=null){
        val result = booksRepository.getBooks(limit, page, genre, author)

        if (result is Result.Success){
            _viewState.postValue(
                _viewState.value?.copy(
                    bookUIList = result.data.map { book ->
                        BookUI(
                            id = book._id,
                            title = book.title,
                            imgUri = book.img.largeFingernail,
                            description = book.description,
                            authors = book.authors,
                            pageCount = book.pageCount,
                            publisher = book.publisher,
                            isWantToRead = false,
                        )
                    },
                    homeSubState = HomeSubState.BookList
                )
            )
        }
        else{
            _viewState.postValue(
                _viewState.value?.copy(
                    homeSubState = HomeSubState.Error
                )
            )
        }
    }

}