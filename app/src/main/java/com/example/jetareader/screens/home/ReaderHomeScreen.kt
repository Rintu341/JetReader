package com.example.jetareader.screens.home

import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.navigation.NavHostController
import com.example.jetareader.components.FabContent
import com.example.jetareader.components.UserTopAppBar
import com.example.jetareader.navigation.ReaderAppScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

@Composable
fun ReaderHomeScreen(navController: NavHostController) {
    var showSignOutDialog by remember { mutableStateOf(false) }
    val currentUserName = FirebaseAuth.getInstance().currentUser?.email?.split('@')?.get(0)
    Scaffold(
        topBar = {
            UserTopAppBar(userName =  currentUserName.toString() ){
                //TODO user log out
                showSignOutDialog = true
            }
        },
        floatingActionButton = {
                FabContent(){
                    //TODO Add books
                }
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier.padding(paddingValues)
                .fillMaxSize()
        ) {
                Text("Home Screen")
        }
    }

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