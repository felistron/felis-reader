package com.felisreader.author.presentation.author_info

sealed class AuthorEvent {
    data class LoadAuthor(val authorId: String): AuthorEvent()
}