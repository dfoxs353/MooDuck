package com.mooduck.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mooduck.data.local.models.BookEntity

@Database(
    entities = [BookEntity::class],
    version = 2,
)
abstract class BookDatabase: RoomDatabase() {

    abstract val dao: BookDao
}