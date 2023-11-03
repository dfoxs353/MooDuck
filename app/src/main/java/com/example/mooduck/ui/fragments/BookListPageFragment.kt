package com.example.mooduck.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mooduck.data.remote.books.BooksResponse
import com.example.mooduck.databinding.FragmentBookListBinding
import com.example.mooduck.ui.adapters.BookListAdapter
import com.example.mooduck.ui.viewmodel.BookListPageViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BookListPageFragment : Fragment() {

    companion object {
        fun newInstance() = BookListPageFragment()
    }

    private lateinit var viewModel: BookListPageViewModel
    private lateinit var binding: FragmentBookListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentBookListBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(this).get(BookListPageViewModel::class.java)


        val layoutManager = LinearLayoutManager(this.context)
        binding.bookRecyclerView.layoutManager = layoutManager

        viewModel.booksResult.observe(viewLifecycleOwner, Observer {
            val booksResult = it ?: return@Observer


            if (booksResult.success !=null){
                binding.loading.visibility = View.GONE
                setBookListData(booksResult.success)
            }
        })

        return view
    }

    override fun onResume() {
        super.onResume()
        getBooks()
    }



    private fun getBooks() {
        GlobalScope.launch(Dispatchers.Main) {
            viewModel.getBooks(limit = 100, page = null, genre = null, author = null)
        }
    }

    private fun setBookListData(bookList:    BooksResponse) {
        val adapter = BookListAdapter(bookList)
        binding.bookRecyclerView.adapter =  adapter
    }

}