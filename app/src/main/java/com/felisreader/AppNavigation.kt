package com.felisreader

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.felisreader.author.presentation.author_info.AuthorScreen
import com.felisreader.chapter.presentation.chapter_lector.LectorScreen
import com.felisreader.chapter.presentation.chapter_list.ChapterListScreen
import com.felisreader.manga.presentation.manga_home.HomeScreen
import com.felisreader.manga.presentation.manga_info.MangaScreen
import com.felisreader.library.presentation.LibraryScreen
import com.felisreader.library.presentation.manga_history.MangaHistoryScreen
import com.felisreader.manga.presentation.manga_search.SearchScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    setTopBarIcon: (@Composable () -> Unit) -> Unit,
    setTopBarTitle: (@Composable () -> Unit) -> Unit,
    resetTopBar: () -> Unit,
    setTopBarVisible: (visible: Boolean) -> Unit,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(
            route = Screen.SearchScreen().route
        ) {
            resetTopBar()

            SearchScreen(
                navigateToInfo = { mangaId ->
                    navController.navigate(Screen.InfoScreen(mangaId).route) {
                        launchSingleTop = true
                    }
                },
                title = it.arguments?.getString("title"),
                tag = it.arguments?.getString("tag")
            )
        }

        composable(
            route = Screen.InfoScreen().route,
            arguments = Screen.InfoScreen().args
        ) {
            it.arguments?.getString("id")?.let { mangaId ->

                setTopBarIcon {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                }

                MangaScreen(
                    mangaId = mangaId,
                    navigateToFeed = {
                        navController.navigate(Screen.ChapterListScreen(mangaId).route) {
                            launchSingleTop = true

                            popUpTo(Screen.InfoScreen(mangaId).route) {
                                inclusive = false
                            }
                        }
                    },
                    searchByTag = { tagId ->
                        navController.navigate(Screen.SearchScreen(tag = tagId).route) {
                            launchSingleTop = true

                            popUpTo(Screen.InfoScreen(mangaId).route) {
                                inclusive = false
                            }
                        }
                    },
                    navigateToAuthor = { authorId ->
                        navController.navigate(Screen.AuthorScreen(authorId = authorId).route) {
                            launchSingleTop = true

                            popUpTo(Screen.InfoScreen(mangaId).route) {
                                inclusive = false
                            }
                        }
                    },
                    setTopBarTitle = setTopBarTitle,
                )
            }
        }

        composable(
            route = Screen.ChapterListScreen().route,
            arguments = Screen.ChapterListScreen().args
        ) {
            setTopBarVisible(true)

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
            setTopBarVisible(false)

            it.arguments?.getString("id")?.let { chapterId ->
                LectorScreen(chapterId = chapterId)
            }
        }

        composable(
            route = Screen.AuthorScreen().route,
            arguments = Screen.AuthorScreen().args
        ) {
            it.arguments?.getString("id")?.let { authorId ->
                AuthorScreen(
                    authorId = authorId,
                    navigateToManga = { mangaId ->
                        navController.navigate(Screen.InfoScreen(mangaId).route) {
                            launchSingleTop = true
                        }
                    },
                    setTopBarTitle = setTopBarTitle
                )
            }
        }

        composable(
            route = Screen.HomeScreen.route,
        ) {
            resetTopBar()

            HomeScreen(
                navigateToManga = { mangaId ->
                    navController.navigate(Screen.InfoScreen(mangaId).route) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(
            route = Screen.LibraryScreen.route,
        ) {
            resetTopBar()

            LibraryScreen(
                navigateToMangaHistory = {
                    navController.navigate(Screen.MangaHistoryScreen.route) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(
            route = Screen.MangaHistoryScreen.route,
        ) {
            setTopBarIcon {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = null
                    )
                }
            }

            setTopBarTitle {
                Text("History")
            }

            MangaHistoryScreen(
                navigateToManga = { mangaId ->
                    navController.navigate(Screen.InfoScreen(mangaId).route) {
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}