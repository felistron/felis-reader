package com.felisreader.manga.domain.model

import com.google.gson.annotations.SerializedName

enum class TagGroup {
    @SerializedName("content")
    CONTENT,
    @SerializedName("format")
    FORMAT,
    @SerializedName("genre")
    GENRE,
    @SerializedName("theme")
    THEME
}
