package com.mooduck.domain.models

data class Book(
    val _id: String,
    val authors: List<String>,
    val geners: List<String>,
    val title: String,
    val img: Images,
    val description: String,
    val pageCount: Int,
    val publisher: String,
)

data class CertainBook(
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
)

data class Comment(
    val bookId: String,
    val date: Int,
    val dislikes: Int,
    val likes: Int,
    val rating: Int,
    val text: String,
    val title: String,
    val userId: String,
    val _id: String,
)

data class Images(
    val largeFingernail: String,
    val mediumFingernail: String,
    val smallFingernail: String,
)

