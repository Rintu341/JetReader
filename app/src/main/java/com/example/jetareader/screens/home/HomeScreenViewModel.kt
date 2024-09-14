package com.example.jetareader.screens.home

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetareader.data.DataOrException
import com.example.jetareader.model.Book
import com.example.jetareader.model.MBook
import com.example.jetareader.repository.FireRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: FireRepository
) : ViewModel(){
        val data : MutableState<DataOrException<List<MBook>,Boolean,Exception>>
                = mutableStateOf(DataOrException(listOf(),true,Exception("")))
        private val _books = MutableStateFlow<List<MBook>>(emptyList())
        val books: StateFlow<List<MBook>> = _books.asStateFlow()

//        init {
//            viewModelScope.launch {
//                getAllDataFromDatabase()
//            }
//        }

        fun getAllDataFromDatabase() {

            viewModelScope.launch {
                data.value.loading = true
                data.value = repository.getAllBooksFromDatabase()
                _books.value = data.value.data?.filter { book ->
                    book.userId == FirebaseAuth.getInstance().currentUser?.uid
                }?: emptyList()
                if(!data.value.data.isNullOrEmpty()) data.value.loading = false
                Log.d("GET", "getAllDataFromDatabase: ${data.value.data.toString()} ")
            }

        }
}