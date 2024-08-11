package com.example.jetareader.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetareader.screens.ReaderSplashScreen
import com.example.jetareader.screens.home.ReaderHomeScreen
import com.example.jetareader.screens.login.ReaderLoginScreen
import com.example.jetareader.screens.search.BookSearchViewModel
import com.example.jetareader.screens.search.SearchScreen
import com.example.jetareader.screens.stats.ReaderStatsScreen

@Composable
fun ReaderNavigation() {
    val navController = rememberNavController()
    val bookSearchViewModel : BookSearchViewModel = viewModel()
    NavHost(navController = navController, startDestination = ReaderAppScreen.SplashScreen.name ) {
        composable(route = ReaderAppScreen.SplashScreen.name) {
            ReaderSplashScreen(navController)
        }
        composable(route = ReaderAppScreen.ReaderHomeScreen.name) {
            ReaderHomeScreen(navController)
        }
        composable(route = ReaderAppScreen.LoginScreen.name) {
            ReaderLoginScreen(navController)
        }
        composable(route = ReaderAppScreen.ReaderStatsScreen.name) {
            ReaderStatsScreen(navController)
        }
        composable(route = ReaderAppScreen.SearchScreen.name) {
            SearchScreen(navController,bookSearchViewModel)
        }
    }

}