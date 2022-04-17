package com.example.smartkitchenandroid.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    @field:Json(name = "id") val uid: String?,
    @field:Json(name = "email") val email: String,
    @field:Json(name = "password") val password: String,
    @field:Json(name = "role") val role: Roles?
)