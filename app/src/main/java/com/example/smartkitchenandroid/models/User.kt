package com.example.smartkitchenandroid.models

import com.squareup.moshi.Json

data class User(
    @field:Json(name = "id") val uid: String?,
    @field:Json(name = "email") val email: String,
    @field:Json(name = "passwordHash") val passwordHash: String,
    @field:Json(name = "role") val role: Roles?
)