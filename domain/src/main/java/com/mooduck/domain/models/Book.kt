package com.mooduck.domain.models


data class BooksPage(
    val bookList: List<Book>,
    val page: Int,
    val pageCount: Int,
)

data class Book(
    val _id: String,
    val authors: List<String>,
    val title: String,
    val img: Images,
    val description: String,
    val pageCount: Int,
    val publisher: String,
)

data class CertainBook(
    val authors: List<String> = listOf(),
    val bookBinding: String = "",
    val bookSeries: String = "",
    val description: String = "",
    val genres: List<String> = listOf(),
    val img: Images = Images("","",""),
    val pageCount: Int = 0,
    val painters: List<String> = listOf(),
    val publishedDate: String = "",
    val _id: String = "",
    val publisher:String = "",
    val title: String = "",
    val translaters: List<String> = listOf(),
)



data class Images(
    val largeFingernail: String,
    val mediumFingernail: String,
    val smallFingernail: String,
)

