package com.example.jetareader.repository

import android.util.Log
import com.example.jetareader.data.DataOrException
import com.example.jetareader.data.Resource
import com.example.jetareader.model.Item
import com.example.jetareader.network.BookApi
import com.google.rpc.context.AttributeContext
import javax.inject.Inject
import kotlin.time.measureTime

class BookRepository @Inject constructor(
    private val bookApi: BookApi
)
{
    suspend fun  getBooks(searchQuery: String) : Resource<List<Item>>
    {
            return  try{
                Resource.Loading(data = true)
                val listItem = bookApi.getAllBooks(searchQuery).items
                if(listItem.toString().isNotEmpty()) Resource.Loading(data = false)
                Resource.Success(data = listItem)
            }catch (
                exception:Exception
            ){
                Resource.Error(massage  = exception.message.toString())
            }
    }

    suspend fun getBookInfo(bookId:String) : Resource<Item>
    {
        val response = try{
                Resource.Loading(data = true)
            val book = bookApi.getBookInfo(bookId)

            Resource.Success(data = book)
        }catch (exception : Exception)
        {
            Resource.Error(massage ="An error occur ${exception.message.toString()}")
        }
        return response;
    }
}





/*
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
 */