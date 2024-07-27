package com.example.jetareader.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetareader.screens.ReaderSplashScreen
import com.example.jetareader.screens.home.ReaderHomeScreen
import com.example.jetareader.screens.login.ReaderLoginScreen

@Composable
fun ReaderNavigation() {
    val navController = rememberNavController()
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
        composable(route = ReaderAppScreen.CreateAccountScreen.name) {
//            CreateAccountScreen()
        }
    }

}