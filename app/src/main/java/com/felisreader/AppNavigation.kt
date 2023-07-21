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
        startDestination = Screen.ChapterListScreen().route
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
        composable(
            route = Screen.ChapterListScreen().route,
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                }
            )
        ) {
            // TODO
            ChapterListScreen(
                mangaId = it.arguments?.getString("id") ?: "a96676e5-8ae2-425e-b549-7f15dd34a6d8",
                navigateToLector = { /*TODO: Navigate to lector screen*/}
            )
        }
    }
}