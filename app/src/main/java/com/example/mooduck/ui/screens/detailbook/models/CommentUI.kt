package com.example.mooduck.ui.screens.detailbook.models

import android.icu.text.CaseMap.Title

data class CommentUI(
    val author: String,
    val date: String,
    val title: String,
    val text: String,
    val likes: Int,
    val dislikes: Int
)
