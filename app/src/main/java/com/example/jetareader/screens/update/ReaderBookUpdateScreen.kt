package com.example.jetareader.screens.update

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.jetareader.components.UserTopAppBar
import com.example.jetareader.navigation.ReaderAppScreen
import com.example.jetareader.screens.home.HomeScreenViewModel

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ReaderUpdateScreen(navController: NavController, bookId:String, viewModel: HomeScreenViewModel = viewModel()) {

        val book = viewModel.data.value.data?.filter { book ->
                book.googleBookId== bookId
        }
        var feedback = remember{
                mutableStateOf("")
        }
        val keyboardController = LocalSoftwareKeyboardController.current
        var searchQuery by remember { mutableStateOf("") }
        val focusManager = LocalFocusManager.current // control focus with in composable
        val valid = remember {
                searchQuery.trim().isNotEmpty()
        }
        val imageState = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                        .data(book?.get(0)?.photoUrl!!.replace("http://", "https://"))
                        .size(Size.ORIGINAL)
                        .build()
                ).state
        Log.d("Book", "ReaderUpdateScreen: $bookId ")
        Scaffold (
                topBar = {
                        UserTopAppBar(navController = navController
                                , title = "Update",
                                icon = Icons.Default.ArrowBack,
                                showProfile = false,
                                omBackArrowPressed = {
                                        navController.navigate(ReaderAppScreen.ReaderHomeScreen.name)
                                }
                        )
                }
        ){padding ->
                Surface(modifier = Modifier.padding(padding)) {
                        Column(
                                modifier = Modifier.padding(10.dp),
                        ) {
                             Card(onClick = {
                                     // navigate to details screen
                             },
                                     modifier = Modifier
                                             .height(200.dp)
                                             .fillMaxWidth()
                                             .align(Alignment.CenterHorizontally),
                                     colors = CardDefaults.cardColors( MaterialTheme.colorScheme.onPrimary)
                             ) {
                                    if(imageState is AsyncImagePainter.State.Loading){
                                            CircularProgressIndicator()
                                    }else if(imageState is AsyncImagePainter.State.Error){
                                            Text(text = "Error")
                                    }else if(imageState is AsyncImagePainter.State.Success){
                                            Image(
                                                    modifier = Modifier.fillMaxSize(),
                                                    painter = imageState.painter, contentDescription = null
                                            )
                                    }
                             }

                                Box(modifier = Modifier
                                        .fillMaxWidth()
                                        .pointerInput(Unit) {
                                                detectTapGestures(onTap = { // detect taps on a Screen
                                                        focusManager.clearFocus() // Clears focus when tapping outside
                                                })
                                        }) {
                                        OutlinedTextField(
                                                modifier = Modifier
                                                        .fillMaxWidth()
                                                        .height(100.dp),
                                                value = searchQuery,
                                                onValueChange = {
                                                        searchQuery = it
                                                },
                                                label = {
                                                        Text(text = "Give your feedback")
                                                },
                                                enabled = true,
                                                keyboardOptions = KeyboardOptions(
                                                        keyboardType = KeyboardType.Text,
                                                        imeAction = ImeAction.Done
                                                ),
                                                keyboardActions = KeyboardActions(
                                                        onDone = {
                                                                focusManager.clearFocus()
                                                        }
                                                ),
                                        )
                                }
                                Spacer(modifier = Modifier.height(10.dp))
                                Row (
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                ){
                                        Button(onClick = {

                                        },
                                                colors = ButtonDefaults.buttonColors(Color.Transparent)
                                        ) {
                                                Text(text = "Start Reading",
                                                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                                        color = Color.Green)
                                        }
                                        Button(onClick = {

                                        },
                                                colors = ButtonDefaults.buttonColors(Color.Transparent)
                                        ) {
                                                Text(text = "Complete",
                                                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                                        color = Color.Yellow)
                                        }
                                }
                                Spacer(modifier = Modifier.height(30.dp))
                                Box(
                                        modifier = Modifier.fillMaxWidth(),
                                        contentAlignment = Alignment.Center
                                )
                                {
                                        Rating(defaultRating = book[0].rating){

                                        }
                                }

                                Row(modifier = Modifier
                                        .fillMaxSize()
                                        .padding(bottom = 20.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.Bottom
                                )
                                {
                                        Button(onClick = {

                                        }) {
                                                Text("Save")
                                        }
                                        Button(onClick = {
                                                navController.navigate(ReaderAppScreen.ReaderHomeScreen.name)
                                        }) {
                                                Text("Cancel")
                                        }
                                }

                        }

                }
        }
}
@Composable
fun Rating(
        defaultRating: Double?,
        enableClear: Boolean = true,  // Option to show clear button
        onRatingChange: (Double) -> Unit, // Callback to pass the updated rating
) {
        var rating by remember { mutableDoubleStateOf(defaultRating ?: 0.0) } // Initial rating as Double
        val maxStars = 5

        Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
        ) {
                // Display the current rating
                Text(
                        text = "Rating: %.1f".format(rating),
                        fontSize = 20.sp,
                        color = Color.Green
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row {
                        for (i in 1..maxStars) {
                                val starRating = i.toDouble()
                                Icon(
                                        imageVector = when {
                                                rating >= starRating -> Icons.Filled.Star
                                                rating >= starRating - 0.5 -> Icons.Filled.StarHalf
                                                else -> Icons.Filled.StarBorder
                                        },
                                        contentDescription = "Star $i",
                                        tint = if (rating >= starRating - 0.5) Color.Yellow else Color.Gray,
                                        modifier = Modifier
                                                .pointerInput(Unit) {
                                                        detectTapGestures(
                                                                onTap = {
                                                                        rating =
                                                                                if (rating == starRating - 0.5) starRating else starRating - 0.5
                                                                        onRatingChange(rating) // Call the callback when rating changes
                                                                },
                                                                onDoubleTap = {
                                                                        rating = starRating
                                                                        onRatingChange(rating) // Call the callback on double tap
                                                                }
                                                        )
                                                }
                                                .size(40.dp)
                                )
                        }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Clear rating option
                if (enableClear) {
                        IconButton(onClick = {
                                rating = 0.0 // Reset rating to 0
                                onRatingChange(rating) // Trigger callback with 0 rating
                        }) {
                                Icon(
                                        imageVector = Icons.Filled.Close,
                                        contentDescription = "Clear Rating",
                                        tint = Color.Red,
                                        modifier = Modifier.size(30.dp)
                                )
                        }
                }
        }
}