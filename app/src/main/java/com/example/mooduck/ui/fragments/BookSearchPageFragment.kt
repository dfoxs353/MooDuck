package com.example.mooduck.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mooduck.R
import com.example.mooduck.ui.viewmodel.BookSearchPageViewModel

class BookSearchPageFragment : Fragment() {

    companion object {
        fun newInstance() = BookSearchPageFragment()
    }

    private lateinit var viewModel: BookSearchPageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_book_search, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BookSearchPageViewModel::class.java)
        // TODO: Use the ViewModel
    }

}