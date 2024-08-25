package com.example.jetareader.screens.search

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.Transition
import com.bumptech.glide.integration.compose.placeholder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.example.jetareader.R
import com.example.jetareader.components.UserTopAppBar
import com.example.jetareader.model.Item
import com.example.jetareader.navigation.ReaderAppScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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
                            bookSearchViewModel.searchBooks(books)
                            Log.d("Search", "SearchScreen: $books ")
                    }
                Spacer(modifier = Modifier.height(5.dp))
                BookListArea(bookSearchViewModel)
            }
        }
    }
}

@Composable
fun BookListArea(
    viewModel : BookSearchViewModel
) {
    val listState = rememberLazyListState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        if(viewModel.isLoading)
        {
            Log.d("BOO", "BookListArea: loading ")
            CircularProgressIndicator()
        }else {
            Log.d(TAG, "BOO: ${viewModel.list}")
            LaunchedEffect(key1 = Unit) {
                listState.animateScrollToItem(index = 0)
            }
            LazyColumn(
                modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                state = listState
            ) {
                items(viewModel.list)
                { item ->
                    SearchBookUI(item = item)
                }
            }
        }
    }



}
/*
//@Preview(showBackground = true)
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
 fun SearchBookUI(modifier: Modifier = Modifier, item: Item) {
//    val imageState = rememberAsyncImagePainter(
//        model = ImageRequest.Builder(LocalContext.current)
//            .data(item.volumeInfo.imageLinks.smallThumbnail)
//            .build()).state
      val imageState : ImageView
    val image = item.volumeInfo.imageLinks.smallThumbnail

    Glide.with(imageState.context)
        .load(image)
        .apply(RequestOptions().placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_foreground))
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: com.bumptech.glide.request.target.Target<Drawable>,
                isFirstResource: Boolean
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onResourceReady(
                resource: Drawable,
                model: Any,
                target: com.bumptech.glide.request.target.Target<Drawable>?,
                dataSource: DataSource,
                isFirstResource: Boolean
            ): Boolean {
                TODO("Not yet implemented")
            }
        })
        .into(imageState)
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
           Box(
               modifier = Modifier.fillMaxWidth(0.2f)
                   .padding(start = 10.dp)
           )
           {
//               Image(painter = imageState.painter!!,
//                   contentDescription =  "image for each book",
//                   contentScale = ContentScale.Crop
//               )
                GlideImage(model = imageState.painter,
                    contentDescription = "simple image",
                    contentScale = ContentScale.Crop,
//                    loading = placeholder(ColorPainter(Color.Red))
                    )

            }
            Column(
                modifier = Modifier.padding(end = 10.dp)
                    .fillMaxWidth(0.8f),
                horizontalAlignment = Alignment.Start
            ){
                Text(text = item.volumeInfo.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.ExtraBold,
                    fontStyle = FontStyle.Italic
                )
                Text(text = "Author: ${item.volumeInfo.authors}",
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic
                )
                Text(text = "Date: ${item.volumeInfo.publishedDate}",
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.Black,
                    maxLines = 1,
                    )
                Text(text = "Category: [${item.volumeInfo.categories}]",
                    color = Color.Black,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    )
            }
        }
    }
}
 */
@Composable
fun LoadImage(
    imageUrl: String,
    onImageLoaded: (ImageBitmap) -> Unit,
    onError: (Throwable?) -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(imageUrl) {
        try {
            val bitmap = withContext(Dispatchers.IO) {
                val glideBitmap = Glide.with(context)
                    .asBitmap()
                    .load(imageUrl)
                    .submit()
                    .get()
                glideBitmap
            }
            onImageLoaded(bitmap.asImageBitmap())
        } catch (e: Exception) {
            onError(e)
        }
    }
}
@Composable
fun SearchBookUI(modifier: Modifier = Modifier, item: Item) {

    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    var error by remember { mutableStateOf<Throwable?>(null) }
    val imageUrl = item.volumeInfo.imageLinks.smallThumbnail

    LoadImage(
        imageUrl = imageUrl,
        onImageLoaded = { bitmap ->
            imageBitmap = bitmap
        },
        onError = { e ->
            error = e
            Log.d("Image Load error", "SearchBookUI: ${e.toString()}")
        }
    )

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(5.dp)
            .clickable {
                // TODO: navigate to details screen
            },
        shape = RoundedCornerShape(10.dp),
        shadowElevation = 5.dp,
//        color = Color(0xFFBDE0FE)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.2f)
                    .padding(start = 10.dp)
            ) {
                // Use GlideImage from Accompanist for image loading
//                GlideImage(
//                    model = item.volumeInfo.imageLinks.smallThumbnail,
//                    contentDescription = "Image for each book",
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier.fillMaxSize()
//                )
                imageBitmap?.let {
                    Image(
                        bitmap = it,
                        contentDescription = "Image for each book",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                error?.let {
                    // Display an error message or placeholder
                    Image(painter = painterResource(id = R.drawable.ic_launcher_background), contentDescription = null)
                }
            }
            Column(
                modifier = Modifier
                    .padding(end = 10.dp)
                    .fillMaxWidth(0.8f),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = item.volumeInfo.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.ExtraBold,
                    fontStyle = FontStyle.Italic
                )
                Text(
                    text = "Author: ${item.volumeInfo.authors}",
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic
                )
                Text(
                    text = "Date: ${item.volumeInfo.publishedDate}",
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.Black,
                    maxLines = 1
                )
                Text(
                    text = "Category: [${item.volumeInfo.categories}]",
                    color = Color.Black,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1
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