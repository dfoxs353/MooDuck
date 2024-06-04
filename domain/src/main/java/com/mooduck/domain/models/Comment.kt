package com.mooduck.domain.models

data class Comment(
    val bookId: String,
    val date: Long,
    val dislikes: List<String>,
    val likes: List<String>,
    val rating: Int,
    val text: String,
    val title: String,
    val userId: String,
    val _id: String,
)
