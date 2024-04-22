package com.mooduck.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class BookEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val _id: String,
    val authors: String,
    val title: String,
    val imgUrls: String,
    val description: String,
    val pageCount: Int,
    val publisher: String,
)



