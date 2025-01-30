package com.felisreader.user.domain.model.api

import com.google.gson.annotations.SerializedName

enum class ReadingStatus {
    @SerializedName("reading")
    READING,

    @SerializedName("on_hold")
    ON_HOLD,

    @SerializedName("plan_to_read")
    PLAN_TO_READ,

    @SerializedName("dropped")
    DROPPED,

    @SerializedName("re_reading")
    RE_READING,

    @SerializedName("completed")
    COMPLETED,
}