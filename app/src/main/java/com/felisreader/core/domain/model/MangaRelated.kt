package com.felisreader.core.domain.model

import com.google.gson.annotations.SerializedName

enum class MangaRelated(val apiName: String) {
    @SerializedName("monochrome")
    MONOCHROME("monochrome"),

    @SerializedName("main")
    MAIN_STORY("main"),

    @SerializedName("adapted_from")
    ADAPTED_FROM("adapted_from"),

    @SerializedName("based_on")
    BASED_ON("based_on"),

    @SerializedName("prequel")
    PREQUEL("prequel"),

    @SerializedName("side_story")
    SIDE_STORY("side_Story"),

    @SerializedName("doujinshi")
    DOUJINSHI("doujinshi"),

    @SerializedName("same_franchise")
    SAME_FRANCHISE("same_franchise"),

    @SerializedName("shared_universe")
    SHARED_UNIVERSE("shared_universe"),

    @SerializedName("sequel")
    SEQUEL("sequel"),

    @SerializedName("spin_off")
    SPIN_OFF("spin_off"),

    @SerializedName("alternate_story")
    ALTERNATE_STORY("alternate_story"),

    @SerializedName("alternate_version")
    ALTERNATE_VERSION("alternate_version"),

    @SerializedName("preserialization")
    PRESERIALIZATION("preserialization"),

    @SerializedName("colored")
    COLORED("colored"),

    @SerializedName("serialization")
    SERIALIZATION("serialization")
}