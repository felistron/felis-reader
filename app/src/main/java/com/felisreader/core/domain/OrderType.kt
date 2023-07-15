package com.felisreader.core.domain

sealed class OrderType(val name: String) {
    object Descending: OrderType("desc")
    object Ascending: OrderType("asc")
}
