package com.example.mooduck.ui.screens.detailbook.models

import android.icu.text.CaseMap.Title

data class CommentUI(
    val id: String,
    val author: String,
    val date: String,
    val title: String,
    val text: String,
    val likes: Int,
    val dislikes: Int,
    var likeState: LikeState = LikeState.None,
)

enum class LikeState{
    DisLike, Like, None
}
