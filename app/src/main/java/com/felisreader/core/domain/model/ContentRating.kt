package com.felisreader.core.domain.model

import com.google.gson.annotations.SerializedName

enum class ContentRating(val value: String) {
    @SerializedName("safe")
    SAFE("Safe"),
    @SerializedName("suggestive")
    SUGGESTIVE("Suggestive"),
    @SerializedName("erotica")
    EROTICA("Erotica"),
    @SerializedName("pornographic")
    PORNOGRAPHIC("Pornographic")
}