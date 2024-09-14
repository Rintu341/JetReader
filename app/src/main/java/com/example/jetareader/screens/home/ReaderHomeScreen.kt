package com.example.jetareader.screens.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.jetareader.components.ContentSection
import com.example.jetareader.components.CurrentReadingSection
import com.example.jetareader.components.FabContent
import com.example.jetareader.components.UserTopAppBar
import com.example.jetareader.model.MBook
import com.example.jetareader.model.getListOfBook
import com.example.jetareader.navigation.ReaderAppScreen
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlin.math.log

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ReaderHomeScreen(navController: NavHostController, homeScreenViewModel: HomeScreenViewModel) {
    var showSignOutDialog by remember { mutableStateOf(false) }
    val currentUserName = FirebaseAuth.getInstance().currentUser?.email?.split('@')?.get(0)
    val scope = rememberCoroutineScope()
    scope.launch {
        homeScreenViewModel.getAllDataFromDatabase()
    }
    val listOfBooks  by homeScreenViewModel.books.collectAsState()


//    if(homeScreenViewModel.data.value.data!!.isNotEmpty()) {
//        listOfBooks = homeScreenViewModel.data.value.data?.filter { book ->
//            book.userId == FirebaseAuth.getInstance().currentUser?.uid
//        }!!
//    }

    Scaffold(
        topBar = {
            UserTopAppBar(
                title = currentUserName.toString(),
                navController = navController
            ) {
                showSignOutDialog = true
            }
        },
        floatingActionButton = {
            FabContent(navController)
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Column {
                if(listOfBooks.isNotEmpty()) {
                    CurrentReadingSection(listOfBooks[0])
                    { it->
                        val route = ReaderAppScreen.UpdateScreen.name
                        navController.navigate("$route/${it.googleBookId}")
                    }
                }else{
                    CurrentReadingSection(MBook())
                    {

                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                if(listOfBooks.isNotEmpty()) {
                    ContentSection(books = listOfBooks, onAllPress = {
                        //TODO navigate to reading list

                    }) { it ->
                        // TODO navigate to specific or clicked book details
                        val route = ReaderAppScreen.UpdateScreen.name
                        navController.navigate("$route/${it.googleBookId}")
                        Log.d("CLick Book", "ReaderHomeScreen: ${it.title}")
                    }
                }else {
                    CircularProgressIndicator()
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
