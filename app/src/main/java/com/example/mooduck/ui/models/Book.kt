package com.example.mooduck.ui.models




data class Book(
    val id: String,
    val title: String,
    val description: String,
    val authors: List<String>,
    val pageCount: Int,
    val publisher: String,
    val isWantToRead: Boolean = false,
    val imgUri: String = "",
)