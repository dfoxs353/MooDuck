package com.mooduck.data.remote

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.mooduck.data.local.BookDatabase
import com.mooduck.data.local.models.BookEntity
import com.mooduck.data.mappers.toBook
import com.mooduck.data.mappers.toBookEntity
import com.mooduck.data.mappers.toBooksPage
import com.mooduck.data.remote.books.BookApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class BookRemoteMediator(
    private val bookDb: BookDatabase,
    private val bookApi: BookApi,
    private val ioDispatcher: CoroutineDispatcher
): RemoteMediator<Int, BookEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, BookEntity>
    ): MediatorResult {
        return try {
            val loadKey = when(loadType){
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null){
                        1
                    }else{
                        (lastItem.id / state.config.pageSize) + 1
                    }
                }
            }

            val books = withContext(ioDispatcher) {
                val response = bookApi.getBooks(limit = state.config.pageSize,page = loadKey)
                response.await()
            }.toBooksPage()

            val endOfPaginationReached = books.pageCount == books.page

            bookDb.withTransaction {
                if (loadType == LoadType.REFRESH){
                    bookDb.dao.clearAll()
                }

                val bookEntities = books.bookList.mapIndexed{ index, book ->
                    BookEntity(
                        id = index + 1 + (loadKey - 1) * state.config.pageSize,
                        _id = book._id,
                        title = book.title,
                        description = book.description,
                        authors = book.authors.joinToString(separator = ","),
                        publisher = book.publisher,
                        pageCount = book.pageCount,
                        imgUrls =
                        "${book.img.largeFingernail}," +
                                "${book.img.mediumFingernail}," +
                                book.img.smallFingernail
                    )
                }
                bookDb.dao.upsertAll(bookEntities)
            }

            MediatorResult.Success(
                endOfPaginationReached = books.bookList.isEmpty() || endOfPaginationReached
            )

        } catch (e: IOException){
            MediatorResult.Error(e)
        } catch (e: HttpException){
            MediatorResult.Error(e)
        }
    }
}