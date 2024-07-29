package com.example.jetareader.components

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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jetareader.R
import com.example.jetareader.model.MBook
import com.example.jetareader.navigation.ReaderAppScreen

//@Preview(showBackground = true, device = Devices.PIXEL_6_PRO, showSystemUi = true )
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserTopAppBar(
    userName: String = "Sujan",
    showProfile: Boolean = true,
    navController: NavController,
    onSignOutClick: () -> Unit = {}
) {
    var showSignOutDialog by remember { mutableStateOf(false) }
    var showSignOutMessage by remember { mutableStateOf(false) }
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
                    text = userName,
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
        colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.onPrimary)
    )
}

//@Preview(showBackground = true)
@Composable
fun ContentSection(label: String = "Reading List") {
    val books = listOf<MBook>(
        MBook(
            id = "1",
            title = "Book1",
            authors = "Author1",
            notes = "Note1",
            photoUrl = R.drawable.richdadpoordad
        ),
        MBook(
            id = "2",
            title = "Book1",
            authors = "Author1",
            notes = "Note1",
            photoUrl = R.drawable.richdadpoordad
        ),
        MBook(
            id = "3",
            title = "Book1",
            authors = "Author1",
            notes = "Note1",
            photoUrl = R.drawable.richdadpoordad
        ),
        MBook(
            id = "4",
            title = "Book1",
            authors = "Author1",
            notes = "Note1",
            photoUrl = R.drawable.richdadpoordad
        ),
        MBook(
            id = "5",
            title = "Book1",
            authors = "Author1",
            notes = "Note1",
            photoUrl = R.drawable.richdadpoordad
        )
    )
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
                    //TODO navigate to reading list
                }
            )
        }
        LazyRow {
            items(books) { book ->
                BookItem(book)
                Spacer(modifier = Modifier.width(5.dp))
            }
        }

    }
}

@Composable
fun BookItem(book: MBook) {
    Surface(
        modifier = Modifier
            .clickable {
                //TODO show user there book details
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
        title = "Book1",
        authors = "Author1",
        notes = "Note1",
        photoUrl = R.drawable.richdadpoordad
    )
) {
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
                    text = "Current Reading",
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
                            .fillMaxWidth(.32f)
                    ) {
                        Image(
                            painter = painterResource(id = book.photoUrl!!),
                            contentDescription = "book image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.clip(shape = RoundedCornerShape(5.dp))
                        )
                    }
                    Column {
                        Icon(
                            imageVector = Icons.Outlined.Star,
                            contentDescription = "star",
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(5.dp)
                                .clickable {
                                    // TODO add book to favorites
                                }
                        )
                    }
                }
            }

        }
    }
}

//@Preview(showBackground = true)
@Composable
fun FabContent(
    onTap: (String) -> Unit = {}
) {
    FloatingActionButton(
        onClick = { onTap("") },
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

