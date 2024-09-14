package com.example.jetareader.screens.details

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.jetareader.R
import com.example.jetareader.components.UserTopAppBar
import com.example.jetareader.data.Resource
import com.example.jetareader.model.Item
import com.example.jetareader.model.MBook
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


@Composable
fun BookDetailsScreen(navController: NavController, detailsViewModel: DetailsViewModel = viewModel(), bookID: String) {


    val bookInfo = produceState<Resource<Item>>(initialValue = Resource.Loading<Item>()) {
        value = detailsViewModel.getBookInfo(bookID)
    }
    val googleBookId = bookInfo.value.data?.id
    val imageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(bookInfo.value.data?.volumeInfo?.imageLinks?.thumbnail?.replace("http://", "https://"))
            .size(Size.ORIGINAL)
            .build()
    ).state

    val titleAnnotated = buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = FontWeight.ExtraBold, fontSize =
        MaterialTheme.typography.titleLarge.fontSize,
            color = Color.Red)) {
            append("Title")
        }
    }
    val localDams = LocalContext.current.resources.displayMetrics
    val context = LocalContext.current
    Scaffold(
        topBar = {
            UserTopAppBar(navController = navController,
                title = " ",
                showProfile = false,
                omBackArrowPressed = {
                    navController.popBackStack()
                }
            ) { }
        })
    {
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            if(bookInfo.value.data == null)
            {
                Column (
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    CircularProgressIndicator()
                }
            }else {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(bottom = 10.dp)
                            .background(
                                Brush.verticalGradient(
                                    listOf(
                                        colorResource(id = R.color.gradiant1), colorResource(
                                            id = R.color.gradiant2
                                        )
                                    )
                                ),
                                RoundedCornerShape(10.dp)
                            ),
                        contentAlignment = Alignment.Center
                    )
                    {
                        if (imageState is AsyncImagePainter.State.Loading){
                            CircularProgressIndicator()
                        }
                        if (imageState is AsyncImagePainter.State.Error){
                            CircularProgressIndicator()
                        }

                        if (imageState is AsyncImagePainter.State.Success){
                            Image(
                                modifier = Modifier.fillMaxSize(),
                                painter = imageState.painter, contentDescription = null
                            )
                        }
                    }
                    Text("${titleAnnotated} : ${bookInfo.value.data!!.volumeInfo.title}")
                    Text(text ="Page count : ${bookInfo.value.data!!.volumeInfo.pageCount}")
                    Text(text ="Published Date : ${bookInfo.value.data!!.volumeInfo.publishedDate}")

                    Spacer(modifier = Modifier.height(30.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(localDams.heightPixels.dp.times(0.12f)),  // based on Screen size it shrink and expand (height)
                    ) {
                        ExpandableCard(
                            title = HtmlCompat.fromHtml(
                                bookInfo.value.data!!.volumeInfo.description,
                                HtmlCompat.FROM_HTML_MODE_LEGACY
                            ).toString()
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    )
                    {
                        Button(onClick = {
                            // save user on firebase
                            val book = MBook(
                                title = bookInfo.value.data!!.volumeInfo.title,
                                authors = bookInfo.value.data!!.volumeInfo.authors.toString(),
                                description = bookInfo.value.data!!.volumeInfo.description,
                                categories = bookInfo.value.data!!.volumeInfo.categories.toString(),
                                notes = "",
                                photoUrl = bookInfo.value.data!!.volumeInfo.imageLinks.thumbnail,
                                publishedDate = bookInfo.value.data!!.volumeInfo.publishedDate,
                                pageCount = bookInfo.value.data!!.volumeInfo.pageCount,
                                rating = 0.0,
                                googleBookId = googleBookId,
                                userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
                            )
                            saveToFirebase(book)
                            {
                                Toast.makeText(context, "book store on fire store", Toast.LENGTH_SHORT).show()
                                navController.popBackStack()
                            }
                        },
                            shape = RoundedCornerShape(50),
                            colors = ButtonDefaults.buttonColors(Color.Green)) {
                            Text(text = "Save")
                        }
                            Button(onClick = {
                                navController.popBackStack()
                            },
                            shape = RoundedCornerShape(50),
                            colors = ButtonDefaults.buttonColors(Color.Blue)) {
                            Text(text = "Cancel", color = Color.White)
                        }
                    }

                }
            }
            }
        }
    }

fun saveToFirebase(book: MBook = MBook(), onSuccess:() -> Unit = {}) {

    val db = FirebaseFirestore.getInstance()
    val dbCollection = db.collection("books")
    if (book.toString().isNotEmpty()) {
        dbCollection.add(book)
            .addOnSuccessListener { documentRef -> // if book store on firebase successfully then it generate an document reference
                val docId = documentRef.id
                dbCollection.document(docId)
                    .update(hashMapOf("id" to docId) as Map<String, Any>) // update id by new document id of book
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            onSuccess.invoke()
                        }
                    }
            }.addOnFailureListener {
                Log.w("Save", "saveToFirebase:  Error updating doc", it)
            }
    }

}// end

@Composable
fun ExpandableCard(title: String) {

    var expanded by remember { mutableStateOf (false) }

        Card(
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable {
                    expanded = !expanded
                }

        ) {
            Text(
                text = if (expanded) "Read Less" else "Read More",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(8.dp)
            )
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                if (expanded) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
