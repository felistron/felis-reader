package com.felisreader.core.domain.model

import com.google.gson.annotations.SerializedName

enum class PublicationDemographic(val apiName: String) {
    @SerializedName("shounen")
    SHOUNEN("shounen"),

    @SerializedName("shoujo")
    SHOUJO("shoujo"),

    @SerializedName("josei")
    JOSEI("josei"),

    @SerializedName("seinen")
    SEINEN("seinen")
}