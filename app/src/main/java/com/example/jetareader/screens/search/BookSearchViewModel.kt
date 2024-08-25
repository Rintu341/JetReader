package com.example.jetareader.screens.search

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetareader.data.Resource
import com.example.jetareader.model.Item
import com.example.jetareader.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookSearchViewModel @Inject constructor(
    private val bookRepository: BookRepository
) : ViewModel()
{
    var list:List<Item> by mutableStateOf(listOf())
    var isLoading by mutableStateOf(false)

    init {
        loadBooks()
    }
    private fun loadBooks()
    {
        searchBooks("android")
    }

    @SuppressLint("SuspiciousIndentation")
    fun searchBooks(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if(query.isEmpty())
                return@launch
            try {
                isLoading = true
                    when(val response = bookRepository.getBooks(query))
                    {
                        is Resource.Success ->
                        {
                            list = response.data!!
                            isLoading = false
                        }
                        is Resource.Error ->
                        {
                            Log.d("NetWork", "Failed to load data: ${response.massage}")
                            isLoading = false
                        }
                        else -> {
                            isLoading = false
                            Log.d("NetWork", "Some things went wrong ")
                        }
                    }
            }catch (exception :Exception)
            {
                Log.d("NetWork", " Error : ${exception.message} ")
            }

        }
    }

}


/*
@HiltViewModel
class BookSearchViewModel @Inject constructor(
    private val bookRepository: BookRepository
) : ViewModel(){
    val listOfBooks : MutableState<DataOrException<List<Item>, Boolean, Exception>>
    = mutableStateOf(DataOrException(listOf(),false,Exception("")))

//    init{
//        searchBooks("Android")
//    }
     fun searchBooks(searchQuery: String)
    {
        viewModelScope.launch{
            if(searchQuery.isEmpty()) {return@launch}


            listOfBooks.value.loading = true
            listOfBooks.value.data = bookRepository.getBooks(searchQuery)
            Log.d("DATA", "searchBooks: ${listOfBooks.value.data.toString()}")
            if(listOfBooks.value.data.toString().isNotEmpty())
                listOfBooks.value.loading = false
        }
    }
}
 */
