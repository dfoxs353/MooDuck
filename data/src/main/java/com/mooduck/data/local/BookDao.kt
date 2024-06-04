package com.mooduck.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.mooduck.data.local.models.BookEntity

@Dao
interface BookDao {

    @Upsert
    fun upsertAll(books: List<BookEntity>)

    @Query("SELECT * FROM bookentity")
    fun pagingSource(): PagingSource<Int, BookEntity>

    @Query("DELETE FROM bookentity")
    fun clearAll(): Int
}