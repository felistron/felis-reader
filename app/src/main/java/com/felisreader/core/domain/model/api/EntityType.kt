package com.felisreader.core.domain.model.api

import com.google.gson.annotations.SerializedName

enum class EntityType(val apiName: String) {
    @SerializedName("manga")
    MANGA("manga"),

    @SerializedName("author")
    AUTHOR("author"),

    @SerializedName("artist")
    ARTIST("artist"),

    @SerializedName("cover_art")
    COVER_ART("cover_art"),

    @SerializedName("tag")
    TAG("tag"),

    @SerializedName("user")
    USER("user"),

    @SerializedName("scanlation_group")
    SCANLATION_GROUP("scanlation_group"),

    @SerializedName("chapter")
    CHAPTER("chapter")
}