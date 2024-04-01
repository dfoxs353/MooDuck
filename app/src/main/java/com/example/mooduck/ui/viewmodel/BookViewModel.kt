package com.example.mooduck.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.mooduck.data.repository.BooksRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val remoteBooksRepository: BooksRepositoryImpl
): ViewModel() {

    suspend fun setToReadBook(id:String){

    }
}