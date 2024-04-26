package com.example.mooduck.ui.screens.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.mooduck.common.EventHandler
import com.example.mooduck.ui.screens.home.models.HomeEvent
import com.example.mooduck.ui.screens.home.models.HomeViewState
import com.mooduck.data.local.models.BookEntity
import com.mooduck.data.mappers.toBook
import com.mooduck.domain.models.BooksPage
import com.mooduck.domain.models.Result
import com.mooduck.domain.repository.BooksRepository
import com.mooduck.domain.repository.LocalUserRepository
import com.mooduck.domain.repository.RemoteUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val booksRepository: BooksRepository,
    private val userRemoteRepository: RemoteUserRepository,
    private val userLocalRepository: LocalUserRepository,
    pager: Pager<Int, BookEntity>,
) : ViewModel(), EventHandler<HomeEvent>{

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

    init {
        getFavoriteBooks()
    }

    override fun obtainEvent(event: HomeEvent) {
        when(event){
            HomeEvent.Refresh -> getFavoriteBooks()
            is HomeEvent.AddedToFavorites -> addToFavoriteBooks(event.value)
            is HomeEvent.DeleteFromFavorites -> deleteFromFavoriteBooks(event.value)
        }
    }

    private fun getFavoriteBooks(){
        viewModelScope.launch{
            val result = userLocalRepository.getUserId()?.let {
                userRemoteRepository.getFavouriteBooks(it,1000,1)
            }

            when(result){
                is Result.Error -> {
                    _viewState.postValue(
                        _viewState.value?.copy(
                            favoriteBooks = BooksPage(
                                bookList = emptyList(),
                                page = 0,
                                pageCount = 0,
                            )
                        )
                    )
                }
                is Result.Success -> {
                    Log.d("TAG", result.data.toString())
                    _viewState.postValue(
                        _viewState.value?.copy(
                            favoriteBooks = result.data
                        )
                    )
                }
                null -> Unit
            }
        }
    }

    private fun addToFavoriteBooks(bookId: String){
        viewModelScope.launch {
            val result = userLocalRepository.getUserId()?.let {
                userRemoteRepository.setFavoriteBook(bookId = bookId, userId = it)
            }

            when(result){
                is Result.Error -> Log.d("ERROR", result.exception.message.toString())
                is Result.Success -> getFavoriteBooks()
                null -> Unit
            }
        }
    }

    private fun deleteFromFavoriteBooks(bookId: String){
        viewModelScope.launch {
            val result = userLocalRepository.getUserId()?.let {
                userRemoteRepository.deleteFavoriteBook(bookId, it)
            }

            when(result){
                is Result.Error -> Log.d("ERROR", result.exception.message.toString())
                is Result.Success -> getFavoriteBooks()
                null -> Unit
            }
        }
    }



}