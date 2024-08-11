package com.example.jetareader.screens.search

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetareader.data.DataOrException
import com.example.jetareader.model.Item
import com.example.jetareader.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.http.Query
import javax.inject.Inject

@HiltViewModel
class BookSearchViewModel @Inject constructor(
    private val bookRepository: BookRepository
) : ViewModel(){
    var listOfBooks : MutableState<DataOrException<List<Item>, Boolean, Exception>>
    = mutableStateOf(DataOrException(null,null,Exception("")))

    init{
        searchBooks("Android")
    }
     fun searchBooks(searchQuery: String)
    {
        viewModelScope.launch (Dispatchers.IO) {
            if(searchQuery.isEmpty()) return@launch

//            listOfBooks.value.loading = true
            listOfBooks.value = bookRepository.getBooks(searchQuery)
            Log.d("DATA", "searchBooks: ${listOfBooks.value.data.toString()}")
            if(listOfBooks.value.data.toString().isNotEmpty())
                listOfBooks.value.loading = false
        }
    }
}