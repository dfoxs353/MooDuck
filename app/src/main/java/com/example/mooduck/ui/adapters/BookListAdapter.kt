package com.example.mooduck.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mooduck.R
import com.example.mooduck.data.model.Book

class BookListAdapter(private val dataSet: ArrayList<Book>):
    RecyclerView.Adapter<BookListAdapter.ViewHolder>(){
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bookTitle: TextView
        val bookAuthor: TextView
        val bookDescription: TextView
        val bookListCount: TextView
        init {
            bookTitle = view.findViewById(R.id.book_title)
            bookAuthor = view.findViewById(R.id.book_author)
            bookDescription = view.findViewById(R.id.book_description)
            bookListCount = view.findViewById(R.id.book_list_count)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.fragment_book, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bookTitle.text = dataSet[position].title
        viewHolder.bookAuthor.text = dataSet[position].author
        viewHolder.bookDescription.text = dataSet[position].description
        viewHolder.bookListCount.text = dataSet[position].list_count
    }

    override fun getItemCount() = dataSet.size
}