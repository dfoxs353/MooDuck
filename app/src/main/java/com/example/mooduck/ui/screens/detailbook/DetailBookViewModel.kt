package com.example.mooduck.ui.screens.detailbook

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mooduck.common.EventHandler
import com.example.mooduck.ui.navigation.NavigationArgs
import com.example.mooduck.ui.screens.detailbook.models.AddCommentState
import com.example.mooduck.ui.screens.detailbook.models.AddCommentSubState
import com.example.mooduck.ui.screens.detailbook.models.CommentUI
import com.example.mooduck.ui.screens.detailbook.models.DetailBookEvent
import com.example.mooduck.ui.screens.detailbook.models.DetailBookSubState
import com.example.mooduck.ui.screens.detailbook.models.DetailBookViewState
import com.example.mooduck.ui.screens.detailbook.models.LikeState
import com.mooduck.domain.models.Result
import com.mooduck.domain.repository.BooksRepository
import com.mooduck.domain.repository.LocalUserRepository
import com.mooduck.domain.repository.RemoteUserRepository
import com.mooduck.domain.usecases.GetUserNameByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class DetailBookViewModel @Inject constructor(
    private val booksRepository: BooksRepository,
    private val remoteUserRepository: RemoteUserRepository,
    private val localUserRepository: LocalUserRepository,
    savedStateHandle: SavedStateHandle,
    private val getUserNameByIdUseCase: GetUserNameByIdUseCase
) : ViewModel(), EventHandler<DetailBookEvent> {

    private val _viewState = MutableLiveData(DetailBookViewState())
    val viewState: LiveData<DetailBookViewState> = _viewState

    private val bookId = savedStateHandle.get<String>(NavigationArgs.BookId.name) ?: ""


    init {
        getDetailBook()
        getBookComments()
        getStateFavoriteBooks()
    }

    override fun obtainEvent(event: DetailBookEvent) {
        when(event){
            DetailBookEvent.onAddFavoriteBookClick -> addToFavoriteBooks()
            DetailBookEvent.getBook -> getDetailBook()
            DetailBookEvent.onDeleteFavoriteBookClick -> deleteFromFavoriteBooks()
            is DetailBookEvent.onDisLikeClick -> addOrDeleteDisLike(event.value)
            is DetailBookEvent.onLikeClick -> addOrDeleteLike(event.value)
            DetailBookEvent.onAddCommentClick -> addedCommentClick()
            DetailBookEvent.onPublishClick -> publishComment()
            is DetailBookEvent.onCommentTextChanged -> commentTextChanged(event.value)
            is DetailBookEvent.onCommentTitleChanged -> commentTitleChanged(event.value)
            DetailBookEvent.dismissAddingComment -> dismissAddingComment()
        }
    }

    private fun dismissAddingComment() {
        _viewState.postValue(
            _viewState.value?.copy(
                isAddComment = false,
                addCommentState = AddCommentState()
            )
        )
    }

    private fun commentTitleChanged(value: String) {
        _viewState.postValue(
            _viewState.value?.addCommentState?.let {
                _viewState.value?.copy(
                    addCommentState = it.copy(
                        title = value
                    )
                )
            }
        )
    }

    private fun commentTextChanged(value: String) {
        _viewState.postValue(
            _viewState.value?.addCommentState?.let {
                _viewState.value?.copy(
                    addCommentState = it.copy(
                        comment = value
                    )
                )
            }
        )
    }

    private fun publishComment() {
        viewModelScope.launch {
            _viewState.postValue(
                _viewState.value?.addCommentState?.let {
                    _viewState.value?.copy(
                        addCommentState = it.copy(
                            addingState = AddCommentSubState.Loading
                        )
                    )
                }
            )

            val title = _viewState.value?.addCommentState?.title ?: ""
            val comment = _viewState.value?.addCommentState?.comment ?: ""

            val result = booksRepository.addCommentToBook(bookId,title,comment,5)

            Log.d("TAG",result.toString())
            when(result){
                is Result.Error -> _viewState.postValue(
                    _viewState.value?.addCommentState?.let {
                        _viewState.value?.copy(
                            addCommentState = it.copy(
                                addingState = AddCommentSubState.Error
                            )
                        )
                    }
                )
                is Result.Success -> _viewState.postValue(
                    _viewState.value?.addCommentState?.let {
                        _viewState.value?.copy(
                            addCommentState = it.copy(
                                addingState = AddCommentSubState.Successful
                            )
                        )
                    }
                )
            }

            getBookComments()
        }
    }

    private fun addedCommentClick() {
        _viewState.postValue(
            _viewState.value?.copy(
                isAddComment = true
            )
        )
    }


    private fun addOrDeleteLike(commentId: String){
        val comment = _viewState.value?.comments?.find {
            it.id == commentId
        }

        if (comment == null)  return

        viewModelScope.launch {
            when(comment.likeState){
                LikeState.DisLike -> {
                    deleteDisLikeToComment(commentId)
                    addLikeToComment(commentId)
                }
                LikeState.Like -> deleteLikeToComment(commentId)
                LikeState.None -> addLikeToComment(commentId)
            }

            getBookComments()
        }
    }

    private suspend  fun addLikeToComment(commentId: String){
        val result = booksRepository.addLikeToComment(commentId)

        when(result){
            is Result.Error -> {}
            is Result.Success -> {
                val comments = _viewState.value?.comments?.map {
                    if (it.id == commentId){
                        it.likeState = LikeState.Like
                    }
                    it
                }

                comments?.let {
                    _viewState.postValue(
                        _viewState.value?.copy(
                            comments = comments
                        )
                    )
                }
            }
        }
    }

    private suspend  fun deleteLikeToComment(commentId: String){
        val result = booksRepository.deleteLikeToComment(commentId)

        when(result){
            is Result.Error -> {}
            is Result.Success -> {
                val comments = _viewState.value?.comments?.map {
                    if (it.id == commentId){
                        it.likeState = LikeState.None
                    }
                    it
                }

                comments?.let {
                    _viewState.postValue(
                        _viewState.value?.copy(
                            comments = comments
                        )
                    )
                }
            }
        }
    }

    private fun addOrDeleteDisLike(commentId: String){
        val comment = _viewState.value?.comments?.find {
            it.id == commentId
        }

        if (comment == null) return

        viewModelScope.launch {
            when(comment.likeState){
                LikeState.DisLike -> deleteDisLikeToComment(commentId)
                LikeState.Like -> {
                    deleteLikeToComment(commentId)
                    addDisLikeToComment(commentId)
                }
                LikeState.None -> addDisLikeToComment(commentId)
            }

            getBookComments()
        }
    }

    private suspend  fun addDisLikeToComment(commentId: String){
        val result = booksRepository.addDisLikeToComment(commentId)

        when(result){
            is Result.Error -> {}
            is Result.Success -> {
                val comments = _viewState.value?.comments?.map {
                    if (it.id == commentId){
                        it.likeState = LikeState.DisLike
                    }
                    it
                }

                comments?.let {
                    _viewState.postValue(
                        _viewState.value?.copy(
                            comments = comments
                        )
                    )
                }
            }
        }
    }

    private suspend  fun deleteDisLikeToComment(commentId: String){
        val result = booksRepository.deleteDisLikeToComment(commentId)

        when(result){
            is Result.Error -> {}
            is Result.Success -> {
                val comments = _viewState.value?.comments?.map {
                    if (it.id == commentId){
                        it.likeState = LikeState.None
                    }
                    it
                }

                comments?.let {
                    _viewState.postValue(
                        _viewState.value?.copy(
                            comments = comments
                        )
                    )
                }
            }
        }
    }

    private fun getDetailBook(){
        viewModelScope.launch {
            _viewState.postValue(
                _viewState.value?.copy(subState = DetailBookSubState.Loading)
            )


            val result = booksRepository.getBook(bookId)

            when(result){
                is Result.Error -> {
                    Log.d("TAG", result.exception.message.toString())
                    _viewState.postValue(
                        _viewState.value?.copy(subState = DetailBookSubState.Error)
                    )
                }
                is Result.Success -> {
                    _viewState.postValue(
                        _viewState.value?.copy(
                            book = result.data,
                            subState = DetailBookSubState.DetailBook,
                        )
                    )
                }
            }
        }
    }

    private fun getBookComments(){
        viewModelScope.launch {


            val result = booksRepository.getBookComments(bookId)

            when(result){
                is Result.Error -> {
                    Log.d("TAG", result.exception.message.toString())
                    _viewState.postValue(
                        _viewState.value?.copy(comments = listOf())
                    )
                }
                is Result.Success -> {
                    _viewState.postValue(
                        _viewState.value?.copy(
                            comments = result.data.map {
                                val userName = getUserNameByIdUseCase(it.userId)
                                val localUserId = localUserRepository.getUserId() ?: ""

                                Log.d("TAG", localUserId + it.likes + it.dislikes)

                                CommentUI(
                                    author = userName,
                                    title = it.title,
                                    text = it.text,
                                    likeState = setLikeState(localUserId, it.likes, it.dislikes),
                                    likes = it.likes.size,
                                    dislikes = it.dislikes.size,
                                    date = convertLongToDate(it.date),
                                    id = it._id,
                                )
                            }
                        )
                    )
                }
            }
        }
    }

    private fun convertLongToDate(longDate: Long): String {
        val date = Date(longDate)
        val format = SimpleDateFormat("yyyy-MM-dd")
        return format.format(date)
    }
    private fun setLikeState(userId: String, likes: List<String>, disLikes: List<String>): LikeState{
        if (likes.contains(userId)) return LikeState.Like
        if (disLikes.contains(userId)) return LikeState.DisLike

        return LikeState.None
    }

    private fun addToFavoriteBooks(){
        viewModelScope.launch {

            val result = localUserRepository.getUserId()?.let {
                remoteUserRepository.setFavoriteBook(bookId = bookId, userId = it)
            }

            when(result){
                is Result.Error -> Log.d("ERROR", result.exception.message.toString())
                is Result.Success -> getStateFavoriteBooks()
                null -> Unit
            }
        }
    }

    private fun deleteFromFavoriteBooks(){
        viewModelScope.launch {

            val result = localUserRepository.getUserId()?.let {
                remoteUserRepository.deleteFavoriteBook(bookId, it)
            }

            when(result){
                is Result.Error -> Log.d("ERROR", result.exception.message.toString())
                is Result.Success -> getStateFavoriteBooks()
                null -> Unit
            }
        }
    }

    private fun getStateFavoriteBooks(){
        viewModelScope.launch{
            val localUserId = localUserRepository.getUserId() ?: ""
            val result = remoteUserRepository.getFavouriteBooks(localUserId,1000,1)

            when(result){
                is Result.Error -> {
                    _viewState.postValue(
                        _viewState.value?.copy(
                            isWantToRead = false
                        )
                    )
                }
                is Result.Success -> {
                    _viewState.postValue(
                        _viewState.value?.copy(
                            isWantToRead = result.data.bookList.any {
                                Log.d("TAG", (bookId == it._id).toString())
                                it._id == bookId
                            }
                        )
                    )
                }
            }
        }
    }
}