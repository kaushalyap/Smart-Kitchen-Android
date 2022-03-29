package com.example.smartkitchenandroid.models

enum class OrderStatus(val status: String) {
    NEW("New"),
    CONFIRMED("Confirmed"),
    READY("Ready"),
    DELIVERED("Delivered")
}