package com.felisreader.core.domain.model

import com.google.gson.annotations.SerializedName

enum class State(val apiName: String) {
    @SerializedName("draft")
    DRAFT("draft"),

    @SerializedName("submitted")
    SUBMITTED("submitted"),

    @SerializedName("published")
    PUBLISHED("published"),

    @SerializedName("rejected")
    REJECTED("rejected")
}