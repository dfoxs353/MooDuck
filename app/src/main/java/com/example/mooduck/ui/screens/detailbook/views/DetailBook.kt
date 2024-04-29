package com.example.mooduck.ui.screens.detailbook.views

import com.mooduck.domain.models.Comment
import com.mooduck.domain.models.Images

data class DetailBook(
    val authors: List<String>,
    val bookBinding: String,
    val bookSeries: String,
    val comments: List<Comment>,
    val description: String,
    val genres: List<String>,
    val img: Images,
    val pageCount: Int,
    val painters: List<String>,
    val publishedDate: String,
    val _id: String,
    val publisher:String,
    val title: String,
    val translaters: List<String>,
    val isWantToRead: Boolean,
)
