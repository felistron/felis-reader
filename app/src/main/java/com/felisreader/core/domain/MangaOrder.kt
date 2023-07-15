package com.felisreader.core.domain

sealed class MangaOrder(val orderType: OrderType, val name: String) {
    class Title(orderType: OrderType): MangaOrder(orderType, "title")
    class Year(orderType: OrderType): MangaOrder(orderType, "year")
    class CreatedAt(orderType: OrderType): MangaOrder(orderType, "createdAt")
    class UpdatedAt(orderType: OrderType): MangaOrder(orderType, "updatedAt")
    class LatestUploadedChapter(orderType: OrderType): MangaOrder(orderType, "latestUploadedChapter")
    class FollowedCount(orderType: OrderType): MangaOrder(orderType, "followedCount")
    class Relevance(orderType: OrderType): MangaOrder(orderType, "relevance")
    class Rating(orderType: OrderType): MangaOrder(orderType, "rating")
}
