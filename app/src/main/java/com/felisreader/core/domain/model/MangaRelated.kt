package com.felisreader.core.domain.model

import com.google.gson.annotations.SerializedName

enum class MangaRelated {
    @SerializedName("monochrome")
    MONOCHROME,
    @SerializedName("main")
    MAIN_STORY,
    @SerializedName("adapted_from")
    ADAPTED_FROM,
    @SerializedName("based_on")
    BASED_ON,
    @SerializedName("prequel")
    PREQUEL,
    @SerializedName("side_story")
    SIDE_STORY,
    @SerializedName("doujinshi")
    DOUJINSHI,
    @SerializedName("same_franchise")
    SAME_FRANCHISE,
    @SerializedName("shared_universe")
    SHARED_UNIVERSE,
    @SerializedName("sequel")
    SEQUEL,
    @SerializedName("spin_off")
    SPIN_OFF,
    @SerializedName("alternate_story")
    ALTERNATE_STORY,
    @SerializedName("alternate_version")
    ALTERNATE_VERSION,
    @SerializedName("preserialization")
    PRESERIALIZATION,
    @SerializedName("colored")
    COLORED,
    @SerializedName("serialization")
    SERIALIZATION
}