package com.example.mooduck.ui.fragments.screens

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.mooduck.data.remote.books.CertainBookResponse
import com.example.mooduck.databinding.FragmentBookPageBinding
import com.example.mooduck.ui.viewmodel.BookPageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BookPageFragment : Fragment() {

    companion object {
        fun newInstance() = BookPageFragment()
    }

    private val viewModel: BookPageViewModel by viewModels()
    private lateinit var binding: FragmentBookPageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookPageBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bookId = arguments?.getString("id")
        Log.d("TAG", "id book: ${bookId}")

        if(bookId != null){
            getBook(bookId)
        }

        viewModel.bookResult.observe(viewLifecycleOwner, Observer {
            val bookResult = it ?: return@Observer

            if(bookResult.error != null){
                //TODO : handler to error
                Log.d("TAG", "Error loading bookPage")
            }
            if(bookResult.success != null){
                Log.d("TAG", "get book success")
                setBookInfo(bookResult.success)
            }
        })


        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setBookInfo(book: CertainBookResponse) {
        val toReadButton = binding.toReadButton

        val bookCover = binding.bookCoverImage
        val bookTitle = binding.titleTextView
        val bookAuthor = binding.authorTextView
        val bookAbout = binding.aboutTextView
        val bookGenre = binding.genreBook
        val bookPublishingHouse = binding.publishingBook
        val bookSeries = binding.seriesBook
        val bookBinding = binding.bindingBook
        val bookArtist = binding.artistBook
        val bookTranslator = binding.translatorBook
        val bookPublishingYear = binding.publishingYearBook
        val bookListCount = binding.listCountBook


        Log.d("TAG", book.toString())
        bookAuthor.text = book.authors.joinToString()
        bookTitle.text = book.title
        bookAbout.text = book.description
        bookGenre.text = book.genres.joinToString()
        bookPublishingHouse.text = book.publisher
        bookSeries.text = book.bookSeries
        bookBinding.text = book.bookBinding
        bookArtist.text = book.painters.joinToString()
        bookTranslator.text = book.translaters.joinToString()
        bookPublishingYear.text = book.publishedDate
        bookListCount.text = book.pageCount.toString()

        try {
            Glide.with(requireContext())
                .load(book.img.largeFingernail)
                .into(bookCover)
        }
        catch (e: Exception){
            Log.d("EXCEPTION", e.toString())
        }
    }

    private fun getBook(bookId: String) {
        GlobalScope.launch(Dispatchers.Main) {
            viewModel.getBook(bookId)
        }
    }

}