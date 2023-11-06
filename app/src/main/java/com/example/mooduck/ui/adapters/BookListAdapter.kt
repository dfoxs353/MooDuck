package com.example.mooduck.ui.adapters

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.mooduck.R
import com.example.mooduck.data.remote.books.BooksResponse

class BookListAdapter(private val dataSet: BooksResponse):
    RecyclerView.Adapter<BookListAdapter.ViewHolder>(){

    private var onClickListener: OnClickListener? = null
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bookTitle: TextView
        val bookAuthor: TextView
        val bookDescription: TextView
        val bookListCount: TextView
        val bookCover: ImageView
        init {
            bookTitle = view.findViewById(R.id.book_title)
            bookAuthor = view.findViewById(R.id.book_author)
            bookDescription = view.findViewById(R.id.book_description)
            bookListCount = view.findViewById(R.id.book_list_count)
            bookCover = view.findViewById(R.id.book_cover)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.fragment_book, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bookTitle.text = dataSet.books[position].title
        viewHolder.bookAuthor.text = dataSet.books[position].authors.joinToString(separator = ",")
        viewHolder.bookDescription.text = dataSet.books[position].description
        viewHolder.bookListCount.text = dataSet.books[position].pageCount.toString() + " / " + dataSet.books[position].publisher
        try {
            val imageUrl = dataSet.books[position].img.largeFingernail
            val into = Glide.with(viewHolder.itemView)
                .load(imageUrl)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        viewHolder.bookCover.setImageResource(R.drawable.cover_book)
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }
                })
                .into(viewHolder.bookCover)
            Log.d("TAG","load image {$imageUrl} and position {$position}")
        }
        catch (e: Exception){
        }

        viewHolder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, dataSet.books[position]._id )
            }
        }
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }
    interface OnClickListener {
        fun onClick(position: Int, bookId: String)
    }
    override fun getItemCount() = dataSet.books.size
}

