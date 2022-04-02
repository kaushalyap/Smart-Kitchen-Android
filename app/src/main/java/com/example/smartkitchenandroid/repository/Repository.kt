package com.example.smartkitchenandroid.repository

import com.example.smartkitchenandroid.api.RetrofitInstance
import com.example.smartkitchenandroid.models.Order
import com.example.smartkitchenandroid.models.User
import retrofit2.Response

class Repository {

    suspend fun getUser(email: String, passwordHash: String): Response<Array<User>> {
        return RetrofitInstance.api.getUser(email, passwordHash)
    }

    suspend fun getOrderByStatus(status: String): Response<List<Order>> {
        return RetrofitInstance.api.getOrderByStatus(status, "timestamp", "desc")
    }

    suspend fun postUpdateOrderStatus(status: String): Response<String> {
        return RetrofitInstance.api.updateOrderStatus(status)
    }
}