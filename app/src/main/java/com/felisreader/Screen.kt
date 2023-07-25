package com.felisreader

sealed class Screen(val route: String) {
    object SearchScreen: Screen(route = "search")
    data class MangaScreen(val id: String = "{id}"): Screen(route = "manga/$id")
    data class ChapterListScreen(val id: String = "{id}"): Screen(route = "manga/$id/chapters")
    data class LectorScreen(val id: String = "{id}"): Screen(route = "lector/$id")
}
