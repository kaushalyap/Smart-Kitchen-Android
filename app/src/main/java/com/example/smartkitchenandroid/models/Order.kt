package com.example.smartkitchenandroid.models

import com.squareup.moshi.Json

data class Order(
    @field:Json(name = "id") val id: String,
    @field:Json(name = "timestamp") val timestamp: String,
    @field:Json(name = "order") val order: String,
    @field:Json(name = "status") val status: OrderStatus,
    @field:Json(name = "price") val price: Int,
)