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

    data class InfoScreen(val mangaId: String = "{id}"):
        Screen(
            route = "manga/$mangaId",
            args = listOf(
                navArgument("id") {
                    type = NavType.StringType
                    nullable = false
                }
            )
        )

    // TODO: Add optional parameter -> Order
    data class ChapterListScreen(val mangaId: String = "{id}"):
        Screen(
            route = "manga/$mangaId/chapters",
            args = listOf(
                navArgument("id") {
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

    data class AuthorScreen(val authorId: String = "{id}"):
        Screen(
            route = "author/$authorId",
            args = listOf(
                navArgument("id") {
                    type = NavType.StringType
                    nullable = false
                }
            )
        )
}
