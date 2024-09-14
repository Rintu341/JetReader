package com.example.jetareader.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jetareader.screens.ReaderSplashScreen
import com.example.jetareader.screens.details.BookDetailsScreen
import com.example.jetareader.screens.details.DetailsViewModel
import com.example.jetareader.screens.home.HomeScreenViewModel
import com.example.jetareader.screens.home.ReaderHomeScreen
import com.example.jetareader.screens.login.ReaderLoginScreen
import com.example.jetareader.screens.search.BookSearchViewModel
import com.example.jetareader.screens.search.SearchScreen
import com.example.jetareader.screens.stats.ReaderStatsScreen
import com.example.jetareader.screens.update.ReaderUpdateScreen

@Composable
fun ReaderNavigation() {
    val navController = rememberNavController()
    val bookSearchViewModel : BookSearchViewModel = viewModel()
    val homeScreenViewModel : HomeScreenViewModel = viewModel()
    val detailsViewModel : DetailsViewModel = viewModel()
    NavHost(navController = navController, startDestination = ReaderAppScreen.SplashScreen.name ) {
        composable(route = ReaderAppScreen.SplashScreen.name) {
            ReaderSplashScreen(navController)
        }
        composable(route = ReaderAppScreen.ReaderHomeScreen.name) {
            ReaderHomeScreen(navController,homeScreenViewModel)
        }
        composable(route = ReaderAppScreen.LoginScreen.name) {
            ReaderLoginScreen(navController)
        }
        val route = ReaderAppScreen.UpdateScreen.name
        composable(route = "$route/{bookId}",
            arguments = listOf(navArgument("bookId"){ type = NavType.StringType})
        ) { navBackStackEntry ->
            navBackStackEntry.arguments?.getString("bookId").let{
                ReaderUpdateScreen(navController, it.toString(),homeScreenViewModel)
            }
        }
        composable(route = ReaderAppScreen.ReaderStatsScreen.name) {
            ReaderStatsScreen(navController)
        }
        composable(route = ReaderAppScreen.SearchScreen.name) {
            SearchScreen(navController,bookSearchViewModel)
        }
        val detailsScreen = ReaderAppScreen.DetailScreen.name

        composable(route = "$detailsScreen/{bookId}", arguments = listOf(navArgument("bookId"){
            type = androidx.navigation.NavType.StringType
        })) { backStackEntry->
            backStackEntry.arguments?.getString("bookId").let{ it ->
            BookDetailsScreen(navController,detailsViewModel,it.toString())
            }
        }
    }

}