package com.example.smartkitchenandroid.api

import com.example.smartkitchenandroid.api.Constants.Companion.HEADER_USER_AGENT
import com.example.smartkitchenandroid.models.Order
import com.example.smartkitchenandroid.models.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.Path

interface SmartKitchenApi {

    @Headers(HEADER_USER_AGENT)
    @GET("user/list?user={user}")
    suspend fun getUser(@Path("user") user: User): Response<User>

    @Headers(HEADER_USER_AGENT)
    @GET("orders/list?status=new&sortTime=asc")
    suspend fun getOrderByStatus(@Path("status") status: String): Response<Array<Order>>

    @Headers(HEADER_USER_AGENT)
    @PATCH("orders/{id}")
    suspend fun updateOrderStatus(@Path("id") id: String): Response<Boolean>
}