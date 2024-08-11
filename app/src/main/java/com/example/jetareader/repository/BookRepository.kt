package com.example.jetareader.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.example.jetareader.data.DataOrException
import com.example.jetareader.model.Book
import com.example.jetareader.model.Item
import com.example.jetareader.network.BookApi
import javax.inject.Inject

class BookRepository @Inject constructor(
    private val bookApi: BookApi
) {
    private var dataOrException = DataOrException<List<Item>,Boolean,Exception>()
    var bookInfoDataOrException = DataOrException<Item,Boolean,Exception>()
    suspend fun getBooks(searchQuery: String) : DataOrException<List<Item>,Boolean,Exception>
    {
        try{
            dataOrException.loading = true
            dataOrException.data = bookApi.getAllBooks(searchQuery).items
            if(dataOrException.data!!.isNotEmpty()) dataOrException.loading = false
            Log.d("DATA_repo", "getBooks: ${dataOrException.data.toString()}")
        }catch (e:Exception)
        {
            Log.d("api", "getBooks: $e ")
            dataOrException.e = e
        }
        return  dataOrException
    }
    suspend fun  getBookInfo(bookId:String) :DataOrException<Item,Boolean,Exception>
    {
       try {
            bookInfoDataOrException.loading = true
            bookInfoDataOrException.data = bookApi.getBookInfo(bookId)
            if(bookInfoDataOrException.data.toString().isNotEmpty())
                bookInfoDataOrException.loading = false
        }catch (e:Exception)
        {
            bookInfoDataOrException.e = e
        }
        return bookInfoDataOrException
    }
}