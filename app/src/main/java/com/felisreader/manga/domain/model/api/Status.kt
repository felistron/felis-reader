package com.felisreader.manga.domain.model.api

import com.google.gson.annotations.SerializedName

enum class Status(val apiName: String) {
    @SerializedName("completed")
    COMPLETED("completed"),

    @SerializedName("ongoing")
    ONGOING("ongoing"),

    @SerializedName("cancelled")
    CANCELLED("cancelled"),

    @SerializedName("hiatus")
    HIATUS("hiatus")
}