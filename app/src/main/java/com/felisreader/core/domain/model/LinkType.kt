package com.felisreader.core.domain.model

sealed class LinkType(val key: String, val relatedSite: String, val url: String) {
    data class AL(val id: String): LinkType("al", "AniList", "https://anilist.co/manga/${id}")
    data class AP(val slug: String): LinkType("ap", "Anime-Planet", "https://www.anime-planet.com/manga/${slug}")
    data class BW(val slug: String): LinkType("bw", "Book Walker", "https://bookwalker.jp/${slug}")
    data class MU(val id: String): LinkType("mu", "MangaUpdates", "https://www.mangaupdates.com/series.html?id=${id}")
    data class NU(val slug: String): LinkType("nu", "NovelUpdates", "https://www.novelupdates.com/series/${slug}")
    data class AMZ(val siteUrl: String): LinkType("amz", "Amazon", siteUrl)
    data class EBJ(val siteUrl: String): LinkType("ebj", "eBookJapan", siteUrl)
    data class MAL(val id: String): LinkType("mal", "MyAnimeList", "https://myanimelist.net/manga/${id}")
    data class CDJ(val siteUrl: String): LinkType("cdj", "CDJapan", siteUrl)
    data class RAW(val siteUrl: String): LinkType("raw", "Official Raw", siteUrl)
    data class ENGTL(val siteUrl: String): LinkType("engtl", "Official English", siteUrl)
}