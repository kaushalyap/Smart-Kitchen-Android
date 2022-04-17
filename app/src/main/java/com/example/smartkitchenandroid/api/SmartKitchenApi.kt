package com.example.smartkitchenandroid.api

import com.example.smartkitchenandroid.api.Constants.Companion.HEADER_USER_AGENT
import com.example.smartkitchenandroid.models.Order
import com.example.smartkitchenandroid.models.User
import retrofit2.Response
import retrofit2.http.*

interface SmartKitchenApi {

    @Headers(HEADER_USER_AGENT)
    @GET("users")
    suspend fun getUser(
        @Query("email") email: String,
        @Query("password") password: String
    ): Response<Array<User>>

    @Headers(HEADER_USER_AGENT)
    @GET("orders")
    suspend fun getOrderByStatus(
        @Query("status") status: String,
        @Query("_sort") sort: String,
        @Query("_order") order: String
    ): Response<List<Order>>

    @Headers(HEADER_USER_AGENT)
    @PATCH("orders/{id}")
    suspend fun updateOrderStatus(@Path("id") id: Int, @Body order: Order): Response<Order>
}