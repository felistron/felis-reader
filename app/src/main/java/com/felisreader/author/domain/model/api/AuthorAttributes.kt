package com.felisreader.author.domain.model.api

data class AuthorAttributes(
    val name: String,
    val imageUrl: String?,
    val biography: Map<String, String>,
    val twitter: String?,
    val pixiv: String?,
    val melonBook: String?,
    val fanBox: String?,
    val booth: String?,
    val nicoVideo: String?,
    val skep: String?,
    val fantia: String?,
    val tumblr: String?,
    val youtube: String?,
    val weibo: String?,
    val naver: String?,
    val namicomi: String?,
    val website: String?,
    val version: Int,
    val createAt: String,
    val updatedAt: String,
)
