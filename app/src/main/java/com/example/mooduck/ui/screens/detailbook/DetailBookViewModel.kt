package com.example.mooduck.ui.screens.detailbook

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mooduck.common.EventHandler
import com.example.mooduck.ui.navigation.NavigationArgs
import com.example.mooduck.ui.screens.detailbook.models.DetailBookEvent
import com.example.mooduck.ui.screens.detailbook.models.DetailBookSubState
import com.example.mooduck.ui.screens.detailbook.models.DetailBookViewState
import com.mooduck.domain.models.Result
import com.mooduck.domain.repository.BooksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailBookViewModel @Inject constructor(
    private val booksRepository: BooksRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel(), EventHandler<DetailBookEvent> {

    private val _viewState = MutableLiveData(DetailBookViewState())
    val viewState: LiveData<DetailBookViewState> = _viewState

    init {
        getDetailBook()
    }

    override fun obtainEvent(event: DetailBookEvent) {
        when(event){
            DetailBookEvent.AddComment -> TODO()
            DetailBookEvent.AddFavoriteBookClick -> TODO()
            DetailBookEvent.DeleteFavoriteBookClick -> TODO()
        }
    }

    private fun getDetailBook(){
        viewModelScope.launch {
            _viewState.postValue(
                _viewState.value?.copy(subState = DetailBookSubState.Loading)
            )

            val bookId = savedStateHandle.get<String>(NavigationArgs.BookId.name)

            if (bookId.isNullOrEmpty()) return@launch

            val result = booksRepository.getBook(bookId)

            when(result){
                is Result.Error -> {
                    _viewState.postValue(
                        _viewState.value?.copy(subState = DetailBookSubState.Error)
                    )
                }
                is Result.Success -> {
                    _viewState.postValue(
                        _viewState.value?.copy(
                            subState = DetailBookSubState.DetailBook,
                            book = result.data
                        )
                    )
                }
            }
        }
    }
}