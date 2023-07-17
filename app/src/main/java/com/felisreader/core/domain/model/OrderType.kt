package com.felisreader.core.domain.model

sealed class OrderType(val name: String) {
    object Descending: OrderType("desc")
    object Ascending: OrderType("asc")
}
