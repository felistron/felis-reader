package com.felisreader

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.felisreader.chapter.presentation.chapter_lector.LectorScreen
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
                navigateToInfo = { mangaId ->
                    navController.navigate(Screen.InfoScreen(mangaId).route) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(
            route = Screen.InfoScreen().route,
            arguments = Screen.InfoScreen().args
        ) {
            it.arguments?.getString("id")?.let { mangaId ->
                MangaScreen(
                    mangaId = mangaId,
                    navigateToFeed = {
                        navController.navigate(Screen.ChapterListScreen(mangaId).route) {
                            launchSingleTop = true

                            popUpTo(Screen.InfoScreen(mangaId).route) {
                                inclusive = false
                            }
                        }
                    }
                )
            }
        }

        composable(
            route = Screen.ChapterListScreen().route,
            arguments = Screen.ChapterListScreen().args
        ) {
            it.arguments?.getString("id")?.let { mangaId ->
                ChapterListScreen(
                    mangaId = mangaId,
                    navigateToLector = { chapterId ->
                        navController.navigate(Screen.LectorScreen(chapterId).route) {
                            launchSingleTop = true
                        }
                    }
                )
            }
        }

        composable(
            route = Screen.LectorScreen().route,
            arguments = Screen.LectorScreen().args
        ) {
            it.arguments?.getString("id")?.let { chapterId ->
                LectorScreen(chapterId = chapterId)
            }
        }
    }
}