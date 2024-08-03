package com.example.jetareader.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.jetareader.R
import com.example.jetareader.components.ContentSection
import com.example.jetareader.components.CurrentReadingSection
import com.example.jetareader.components.FabContent
import com.example.jetareader.components.UserTopAppBar
import com.example.jetareader.model.MBook
import com.example.jetareader.navigation.ReaderAppScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

@Composable
fun ReaderHomeScreen(navController: NavHostController) {
    var showSignOutDialog by remember { mutableStateOf(false) }
    val currentUserName = FirebaseAuth.getInstance().currentUser?.email?.split('@')?.get(0)
    //Dummy data for testing
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

    Scaffold(
        topBar = {
            UserTopAppBar(
                userName = currentUserName.toString(),
                navController = navController
            ) {
                //TODO user log out
                showSignOutDialog = true
            }
        },
        floatingActionButton = {
            FabContent() {
                //TODO Add books
            }
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Column {
                CurrentReadingSection()
                Spacer(modifier = Modifier.height(10.dp))
                ContentSection("Reading List", books = books, onAllPress = {
                    //TODO navigate to reading list
                }) {
                    // TODO navigate to specific or clicked book details
                }
            }
        }
    }

    //Show user is really signed out and navigate to login screen
    if (showSignOutDialog) {
        AlertDialog(
            onDismissRequest = { showSignOutDialog = false },
            title = { Text(text = "Exit") },
            text = { Text(text = "Are you sure you want to sign out?") },
            confirmButton = {
                TextButton(onClick = {
                    showSignOutDialog = false
                    // Handle sign out action here
                    FirebaseAuth.getInstance().signOut().run {
                        navController.navigate(ReaderAppScreen.LoginScreen.name)
                    }
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showSignOutDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
