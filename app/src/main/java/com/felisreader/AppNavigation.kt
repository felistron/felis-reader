package com.felisreader

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.felisreader.chapter.presentation.chapter_list.ChapterListScreen
import com.felisreader.manga.presentation.manga_info.MangaScreen
import com.felisreader.manga.presentation.manga_search.SearchScreen

@RequiresApi(Build.VERSION_CODES.O)
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
                mangaId = it.arguments?.getString("id").toString(),
                navigateToFeed = { id ->
                    navController.navigate(Screen.ChapterListScreen(id).route) {
                        launchSingleTop = true
                        popUpTo(Screen.MangaScreen(id).route) {
                            inclusive = false
                        }
                    }
                }
            )
        }
        composable(
            route = Screen.ChapterListScreen().route,
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                }
            )
        ) {
            it.arguments?.getString("id")?.let { it1 ->
                ChapterListScreen(
                    mangaId = it1,
                    navigateToLector = { /*TODO: Navigate to lector screen*/ }
                )
            }
        }
    }
}