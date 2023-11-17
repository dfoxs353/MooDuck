package com.example.mooduck.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mooduck.common.RetrofitClient
import com.example.mooduck.data.remote.books.BookApi
import com.example.mooduck.data.repository.BooksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val remoteBooksRepository: BooksRepository
): ViewModel() {

    suspend fun setToReadBook(id:String){

    }
}