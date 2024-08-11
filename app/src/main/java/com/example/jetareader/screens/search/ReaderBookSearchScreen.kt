package com.example.jetareader.screens.search

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jetareader.R
import com.example.jetareader.components.UserTopAppBar
import com.example.jetareader.model.MBook
import com.example.jetareader.model.getListOfBook
import com.example.jetareader.navigation.ReaderAppScreen

//@Preview(showBackground = true, device = Devices.PIXEL_6_PRO)
@Composable
fun SearchScreen(navController: NavController = NavController(LocalContext.current),
    bookSearchViewModel: BookSearchViewModel ) {
    Scaffold(
        topBar = {
                UserTopAppBar(navController = navController,
                    title = "Search",
                    showProfile = false,
                    icon = Icons.Filled.ArrowBack,
                    omBackArrowPressed = {
                        navController.navigate(ReaderAppScreen.ReaderHomeScreen.name)
                    }
                )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            Column() {
                SearchForm(modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                    bookSearchViewModel = bookSearchViewModel
                ){ books ->
//                            bookSearchViewModel.searchBooks(books)
                            Log.d("Search", "SearchScreen: $books ")
                    }
                Spacer(modifier = Modifier.height(5.dp))
                BookListArea(
                        getListOfBook()
                )
            }
        }
    }
}

@Composable
fun BookListArea(
    listOfBooks: List<MBook> = emptyList()
) {
    val listState = rememberLazyListState()
    LaunchedEffect(key1 = Unit) {
        listState.animateScrollToItem(index = 0)
    }
        LazyColumn(
            modifier = Modifier.padding(start = 10.dp, end = 10.dp),
            state = listState
        ) {
            items(listOfBooks){
                SearchBookUI()
            }
        }
}
@Preview(showBackground = true)
@Composable
fun SearchBookUI(modifier: Modifier = Modifier,mbook: MBook = MBook(photoUrl = R.drawable.richdadpoordad,
    title = "Rich Dad Poor Dad",
    authors = "Robert Kiyosaki and Sharon L. Lechter",
    publishedDate = "1997",
    categories = listOf("Personal Finance", "Business")
)) {
    Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(5.dp)
                .clickable {
                    //TODO navigate to details screen
                },
            shape = RoundedCornerShape(10.dp),
            shadowElevation = 5.dp,
            color = Color(0xFFBDE0FE)
    ) {
        Row (
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Surface(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .fillMaxWidth(0.2f)
                    .fillMaxHeight(),
                    color = Color(0xFFBDE0FE)
            ) {
               Image(painter = painterResource(id = mbook.photoUrl!!),
                   contentDescription =  "image for each book",
                   contentScale = ContentScale.Fit)
            }
            Column(
                modifier = Modifier.padding(end = 10.dp),
                horizontalAlignment = Alignment.Start
            ){
                Text(text = mbook.title.toString(),
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.ExtraBold,
                    fontStyle = FontStyle.Italic
                )
                Text(text = "Author: ${mbook.authors.toString()}",
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic
                )
                Text(text = "Date: ${mbook.publishedDate.toString()}",
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.Black,
                    maxLines = 1,
                    )
                Text(text = "Category: [${mbook.categories?.get(0)?.toString()}]",
                    color = Color.Black,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    )
            }
        }
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun SearchForm(
    modifier: Modifier = Modifier,
    loading:Boolean = false,
    hint: String = "Search",
    bookSearchViewModel: BookSearchViewModel,
    onSearch:(String) -> Unit = {}
)
{
    Column() {
        val searchQueryState = rememberSaveable {
            mutableStateOf("")
        }
        val keyboardController = LocalSoftwareKeyboardController.current
        val searchQueryValue = remember {
            mutableStateOf("")
        }
        val valid = remember {
            searchQueryState.value.trim().isNotEmpty()
        }
        /*
        InputField(modifier = modifier,
            valueState = searchQueryState,
            labelId = hint,
            enabled = true,
            onAction = KeyboardActions{
                    if(!valid) return@KeyboardActions
                    onSearch(searchQueryState.value.trim())
                bookSearchViewModel.searchBooks(searchQueryState.value.trim())
                    searchQueryState.value = ""
                    keyboardController?.hide()
            },
            imeAction = ImeAction.Search
        )
         */

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            OutlinedTextField(
                modifier = Modifier

                    .padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
                value = searchQueryState.value,
                onValueChange = {
                    searchQueryState.value = it
                },
                label = {
                    Text(text = hint)
                },
                enabled = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                ),
                maxLines = 1,
                singleLine = true
            )
            Spacer(modifier = Modifier.width(5.dp))
            IconButton(onClick = {
                if (!valid) {
                    Log.d("search", "searchClick ")
                    onSearch(searchQueryState.value.trim())
                    bookSearchViewModel.searchBooks(searchQueryState.value.trim())
                }
            }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            }
        }
    }
}