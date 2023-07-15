package com.felisreader.core.domain

import com.google.gson.annotations.SerializedName

enum class Status(val value: String) {
    @SerializedName("completed")
    COMPLETED("Completed"),
    @SerializedName("ongoing")
    ONGOING("Ongoing"),
    @SerializedName("cancelled")
    CANCELLED("Cancelled"),
    @SerializedName("hiatus")
    HIATUS("Hiatus")
}