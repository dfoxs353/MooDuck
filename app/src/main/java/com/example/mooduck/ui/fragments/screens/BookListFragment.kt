package com.example.mooduck.ui.fragments.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mooduck.R
import com.example.mooduck.data.remote.books.BooksResponse
import com.example.mooduck.databinding.FragmentBookListBinding
import com.example.mooduck.ui.adapters.BookListAdapter
import com.example.mooduck.ui.viewmodel.BookListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BookListFragment : Fragment() {

    companion object {
        fun newInstance() = BookListFragment()
    }

    private val viewModel: BookListViewModel by viewModels()
    private lateinit var binding: FragmentBookListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookListBinding.inflate(inflater, container, false)


        val layoutManager = LinearLayoutManager(this.context)
        binding.bookRecyclerView.layoutManager = layoutManager

        viewModel.booksResult.observe(viewLifecycleOwner, Observer {
            val booksResult = it ?: return@Observer

            if (booksResult.success !=null){
                binding.loading.visibility = View.GONE
                setBookListData(booksResult.success)
            }
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.loading.visibility = View.VISIBLE
        getBooks()
    }

    private fun getBooks() {
        GlobalScope.launch(Dispatchers.Main) {
            viewModel.getBooks(limit = 100, page = null, genre = null, author = null)
        }
    }

    private fun setBookListData(bookList:    BooksResponse) {
        val adapter = BookListAdapter(bookList)
        adapter.setOnClickListener(object :
            BookListAdapter.OnClickListener{
            override fun onClick(position: Int, bookId: String) {
                val bundle = Bundle()
                bundle.putString("id", bookId)
                findNavController().navigate(R.id.action_bookListFragment_to_bookPageFragment, bundle)
            }
            }
        )
        binding.bookRecyclerView.adapter =  adapter
    }

    private fun setFavouriteBook(bookId: String, localUserRepository: LocalUserRepository) {
        GlobalScope.launch(Dispatchers.Main){
            if(localUserRepository.getUserId() != null){
                viewModel.setFavouriteBook(bookId,localUserRepository.getUserId()!!)
            }
        }
    }

}