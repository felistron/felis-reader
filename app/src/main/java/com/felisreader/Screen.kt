package com.felisreader

sealed class Screen(val route: String) {
    object SearchScreen: Screen(route = "search_screen")
    data class MangaScreen(val id: String = "{id}"): Screen(route = "manga_screen/$id")
    data class ChapterListScreen(val id: String = "{id}"): Screen(route = "manga_screen/${id}/chapters")
}
