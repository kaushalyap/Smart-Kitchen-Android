package com.example.smartkitchenandroid.models

import com.squareup.moshi.Json

data class Order(
    @field:Json(name = "id") val id: String,
    @field:Json(name = "order") val order: String,
    @field:Json(name = "status") val status: OrderStatus
)