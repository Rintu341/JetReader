package com.example.jetareader.components

import android.icu.text.CaseMap.Title
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.example.jetareader.R
import com.example.jetareader.model.MBook
import com.example.jetareader.navigation.ReaderAppScreen

//@Preview(showBackground = true, device = Devices.PIXEL_6_PRO, showSystemUi = true )
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserTopAppBar(
    title: String = "Sujan",
    showProfile: Boolean = true,
    icon: ImageVector = Icons.Default.ArrowBack,
    navController: NavController,
    omBackArrowPressed: () -> Unit = {},
    onSignOutClick: () -> Unit = {}
) {
    var showSignOutDialog by remember { mutableStateOf(false) }
    var showSignOutMessage by remember { mutableStateOf(false) }
    if(showProfile) {
        TopAppBar(
            title = {
                Row(
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // User Icon
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(15))
                            .clickable {
                                navController.navigate(ReaderAppScreen.ReaderStatsScreen.name)
                            }
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    // User Name
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    // Sign Out Icon
                    IconButton(onClick = {
                        onSignOutClick.invoke()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Sign Out",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            },
            actions = {},
//            colors =TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.onPrimary)
            colors = TopAppBarDefaults.topAppBarColors(colorResource(id = R.color.topBar))
        )
    }else{
        TopAppBar(title = { Text(text = title,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            fontWeight = FontWeight.ExtraBold) },
            navigationIcon = {
                IconButton(onClick = {
                    omBackArrowPressed.invoke()
                }) {
                    Icon(imageVector = icon,
                        contentDescription = "navigate",)
                }
            })
    }
}

@Preview(showBackground = true)
@Composable
fun ContentSection(
    label: String = "Reading List",
    onAllPress: () -> Unit = {},
    books: List<MBook> = emptyList(),
    onBookPress: (MBook) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(start = 10.dp, end = 10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp)
                .fillMaxHeight(.2f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Text(
                text = label, fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontWeight = FontWeight.Bold
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Arrow",
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.clickable {
                    //navigate to reading list
                    onAllPress.invoke()
                }
            )
        }
        LazyRow {
            items(books) { book ->
                BookItem(book) {
                    onBookPress.invoke(book)
                }
                Spacer(modifier = Modifier.width(5.dp))
            }
        }

    }
}

@Composable
fun BookItem(book: MBook, onPress: (MBook) -> Unit = {}) {
    Surface(
        modifier = Modifier
            .clickable {
                //navigate to details screen for clicked book
                onPress(book)
            }
            .padding(3.dp)
            .width(90.dp)
            .height(150.dp),
    ) {
        Image(
            painter = painterResource(id = book.photoUrl!!),
            contentDescription = "book image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.clip(shape = RoundedCornerShape(5.dp))
        )
    }
}

//TODO
@Preview(showBackground = true)
@Composable
fun CurrentReadingSection(
    book: MBook = MBook(
        id = "5",
        title = " Rich Dad Poor Dad",
        authors = "Robert Kiyosaki and Sharon L. Lechter",
        notes = "Note1",
        photoUrl = R.drawable.richdadpoordad
    )
) {
    val sectionTag = remember {
            mutableStateOf("Currently Reading")
    }
    BoxWithConstraints {
        val screenWidth = maxWidth
        val screenHeight = maxHeight
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 5.dp)
                    .fillMaxHeight(.2f),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Text(
                    text = sectionTag.value,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Arrow",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.clickable {
                        //TODO navigate to reading list
                    }
                )
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(Color(0xFF7C95B6)),
                elevation = CardDefaults.cardElevation(5.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Surface(
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .fillMaxHeight()
                            .fillMaxWidth(.32f),
                        color = Color(0xFF7C95B6)
                    ) {
                        Image(
                            painter = painterResource(id = book.photoUrl!!),
                            contentDescription = "book image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.clip(shape = RoundedCornerShape(5.dp))
                        )
                    }
                    Column(
                        modifier = Modifier.padding(5.dp),
                        horizontalAlignment = Alignment.End
                    ) {

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            RoundedButton()
                            Column {
                                Icon(
                                    imageVector = Icons.Outlined.FavoriteBorder,
                                    contentDescription = "star",
                                    tint = MaterialTheme.colorScheme.onBackground,
                                    modifier = Modifier
                                        .clickable {
                                            // TODO add book to favorites
                                        }
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                BookRating(score = 4.5)
                            }
                        }
                        Column(
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = book.title!!,
                                fontWeight = FontWeight.ExtraBold,
                                fontStyle = FontStyle.Italic,
                                style = MaterialTheme.typography.titleLarge,
                                maxLines = 1,
                                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .padding(end = 5.dp),
                                textAlign = TextAlign.End,
                            )
                            Text(
                                text = "Author: ${book.authors}",
                                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                                fontStyle = FontStyle.Italic,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(end = 5.dp),
                                textAlign = TextAlign.End,
                                maxLines = 2,
                                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                            )
                        }
                    }
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RoundedButton(
    label: String = "Reading",
    radius: Int = 29,
    onPress: () -> Unit = {}
) {
    Surface(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth(.32f)
            .fillMaxHeight(0.2f),
        shape = RoundedCornerShape(10),
        color = Color(0xFF48CAE4)
    ) {
        Column(
            modifier = Modifier
                .clickable {
                    onPress.invoke()
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = label,
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun BookRating(score: Double) {
    Surface(
        modifier = Modifier
            .padding(top = 5.dp)
            .height(60.dp),
        color = Color(0xFF7C95B6)
    ) {
        Column(

        ) {
            Icon(
                imageVector = Icons.Outlined.Star,
                contentDescription = "star",
                tint = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = score.toString(),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

//@Preview(showBackground = true)
@Composable
fun FabContent(
    navController: NavController
) {
    FloatingActionButton(
        onClick = { navController.navigate(ReaderAppScreen.SearchScreen.name)},
        containerColor = Color(0xFF06497E),
        shape = RoundedCornerShape(50)
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add a Book",
            tint = Color.White
        )
    }
}

