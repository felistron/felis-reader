package com.felisreader.core.domain

import com.google.gson.annotations.SerializedName

enum class PublicationDemographic(val value: String) {
    @SerializedName("shounen")
    SHOUNEN("Shounen"),
    @SerializedName("shoujo")
    SHOUJO("Shoujo"),
    @SerializedName("josei")
    JOSEI("Josei"),
    @SerializedName("seinen")
    SEINEN("Seinen")
}