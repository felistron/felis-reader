package com.felisreader

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(val route: String, val args: List<NamedNavArgument> = emptyList()) {
    // TODO: Add optional parameters -> Title, tag and author
    object SearchScreen: Screen(route = "search")

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
}
