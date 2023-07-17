package com.felisreader

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.felisreader.manga.presentation.manga_info.MangaScreen
import com.felisreader.manga.presentation.manga_search.SearchScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.SearchScreen.route
    ) {
        composable(
            route = Screen.SearchScreen.route
        ) {
            SearchScreen(
                onNavigateToInfo = { id ->
                    navController.navigate(Screen.MangaScreen(id).route) {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(
            route = Screen.MangaScreen().route,
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                }
            )
        ) {
            MangaScreen(
                id = it.arguments?.getString("id").toString()
            )
        }
    }
}