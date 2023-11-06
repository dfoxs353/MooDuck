package com.example.mooduck.ui.fragments.screens

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mooduck.R
import com.example.mooduck.ui.viewmodel.BookPageViewModel

class BookPageFragment : Fragment() {

    companion object {
        fun newInstance() = BookPageFragment()
    }

    private lateinit var viewModel: BookPageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_book_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(BookPageViewModel::class.java)

        val bookId = arguments?.getString("id")
        Log.d("TAG", "id book: ${bookId}")
        // TODO: Use the ViewModel
    }

}