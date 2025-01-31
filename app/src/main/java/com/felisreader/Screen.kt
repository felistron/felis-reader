package com.felisreader

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(
    val route: String,
    val args: List<NamedNavArgument> = emptyList()
) {
    data class SearchScreen(
        val title: String? = "{title}",
        val tag: String? = "{tag}",
    ):
        Screen(
            route = "search?title=$title&tag=$tag}",
            args = listOf(
                navArgument("title") {
                    type = NavType.StringType
                    nullable = true
                },
                navArgument("tag") {
                    type = NavType.StringType
                    nullable = true
                },
            )
        )

    data class InfoScreen(
        val mangaId: String = "{id}",
        val rootScreen: String = "{root}",
    ):
        Screen(
            route = "manga/$mangaId?root=$rootScreen",
            args = listOf(
                navArgument("id") {
                    type = NavType.StringType
                    nullable = false
                },
                navArgument("root") {
                    type = NavType.StringType
                    nullable = false
                }
            )
        )

    // TODO: Add optional parameter -> Order
    data class ChapterListScreen(
        val mangaId: String = "{id}",
        val rootScreen: String = "{root}"
    ):
        Screen(
            route = "manga/$mangaId/chapters?root=$rootScreen",
            args = listOf(
                navArgument("id") {
                    type = NavType.StringType
                },
                navArgument("root") {
                    type = NavType.StringType
                }
            )
        )

    // TODO: Add optional parameter -> Page
    data class LectorScreen(val chapterId: String = "{id}"):
        Screen(
            route = "lector/$chapterId",
            args = listOf(
                navArgument("id") {
                    type = NavType.StringType
                    nullable = false
                }
            )
        )

    data class AuthorScreen(
        val authorId: String = "{id}",
        val rootScreen: String = "{root}",
    ):
        Screen(
            route = "author/$authorId?root=$rootScreen",
            args = listOf(
                navArgument("id") {
                    type = NavType.StringType
                    nullable = false
                },
                navArgument("root") {
                    type = NavType.StringType
                    nullable = false
                }
            )
        )

    object HomeScreen: Screen(route = "home")

    object LibraryScreen: Screen(route = "library")

    data class MangaHistoryScreen(
        val rootScreen: String = "{root}"
    ) : Screen(
        route = "library/manga_history?root=$rootScreen",
        args = listOf(
            navArgument("root") {
                type = NavType.StringType
                nullable = false
            }
        )
    )

    data class ReadingHistoryScreen(
        val rootScreen: String = "{root}"
    ): Screen(
        route = "library/reading_history?root=$rootScreen",
        args = listOf(
            navArgument("root") {
                type = NavType.StringType
                nullable = false
            }
        )
    )
}
