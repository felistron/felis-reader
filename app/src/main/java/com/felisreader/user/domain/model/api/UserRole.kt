package com.felisreader.user.domain.model.api

import com.google.gson.annotations.SerializedName

enum class UserRole {
    @SerializedName("ROLE_ADMIN")
    ADMIN,

    @SerializedName("ROLE_BANNED")
    BANNED,

    @SerializedName("ROLE_CONTRIBUTOR")
    CONTRIBUTOR,

    @SerializedName("ROLE_DESIGNER")
    DESIGNER,

    @SerializedName("ROLE_DEVELOPER")
    DEVELOPER,

    @SerializedName("ROLE_FORUM_MODERATOR")
    FORUM_MODERATOR,

    @SerializedName("ROLE_GLOBAL_MODERATOR")
    GLOBAL_MODERATOR,

    @SerializedName("ROLE_GROUP_LEADER")
    GROUP_LEADER,

    @SerializedName("ROLE_GUEST")
    GUEST,

    @SerializedName("ROLE_MEMBER")
    MEMBER,

    @SerializedName("ROLE_MD_AT_HOME")
    MD_AT_HOME,

    @SerializedName("ROLE_POWER_UPLOADER")
    POWER_UPLOADER,

    @SerializedName("ROLE_PUBLIC_RELATIONS")
    PUBLIC_RELATIONS,

    @SerializedName("ROLE_STAFF")
    STAFF,

    @SerializedName("ROLE_UNVERIFIED")
    UNVERIFIED,

    @SerializedName("ROLE_USER")
    USER,

    @SerializedName("ROLE_VIP")
    VIP,
}