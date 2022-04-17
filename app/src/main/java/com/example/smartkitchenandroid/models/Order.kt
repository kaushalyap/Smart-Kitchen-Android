package com.example.smartkitchenandroid.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Order(
    @field:Json(name = "id") val id: String,
    @field:Json(name = "timestamp") val timestamp: String,
    @field:Json(name = "order") val order: String,
    @field:Json(name = "status") var status: OrderStatus,
    @field:Json(name = "price") val price: Int,
)