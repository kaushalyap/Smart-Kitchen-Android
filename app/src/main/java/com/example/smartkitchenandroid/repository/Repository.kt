package com.example.smartkitchenandroid.repository

import com.example.smartkitchenandroid.api.RetrofitInstance
import com.example.smartkitchenandroid.models.Order
import com.example.smartkitchenandroid.models.User
import retrofit2.Response

class Repository {

    suspend fun getUser(user: User): Response<User> {
        return RetrofitInstance.api.getUser(user)
    }

    suspend fun getOrderByStatus(status: String): Response<Array<Order>> {
        return RetrofitInstance.api.getOrderByStatus(status)
    }

    suspend fun postUpdateOrderStatus(status: String): Response<Boolean> {
        return RetrofitInstance.api.updateOrderStatus(status)
    }
}