package com.felisreader.chapter.domain.model.api

import com.felisreader.core.domain.model.OrderType

sealed class ChapterOrder(var orderType: OrderType, val name: String) {
    class Created(orderType: OrderType): ChapterOrder(orderType, "createdAt")
    class Updated(orderType: OrderType): ChapterOrder(orderType, "updatedAt")
    class Publish(orderType: OrderType): ChapterOrder(orderType, "publishAt")
    class Readable(orderType: OrderType): ChapterOrder(orderType, "readableAt")
    class Volume(orderType: OrderType): ChapterOrder(orderType, "volume")
    class Chapter(orderType: OrderType): ChapterOrder(orderType, "chapter")
}
