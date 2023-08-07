package com.felisreader.core.domain.model

import com.google.gson.annotations.SerializedName

enum class ContentRating(val apiName: String) {
    @SerializedName("safe")
    SAFE("safe"),

    @SerializedName("suggestive")
    SUGGESTIVE("suggestive"),

    @SerializedName("erotica")
    EROTICA("erotica"),

    @SerializedName("pornographic")
    PORNOGRAPHIC("pornographic")
}