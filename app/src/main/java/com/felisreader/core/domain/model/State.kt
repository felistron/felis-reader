package com.felisreader.core.domain.model

import com.google.gson.annotations.SerializedName

enum class State {
    @SerializedName("draft")
    DRAFT,
    @SerializedName("submitted")
    SUBMITTED,
    @SerializedName("published")
    PUBLISHED,
    @SerializedName("rejected")
    REJECTED
}