package com.example.smartkitchenandroid.repository

import com.example.smartkitchenandroid.api.RetrofitInstance
import com.example.smartkitchenandroid.api.SimpleResponse
import com.example.smartkitchenandroid.models.Order
import com.example.smartkitchenandroid.models.User
import retrofit2.Response

class Repository {

    suspend fun getUser(email: String, password: String): SimpleResponse<Array<User>> {
        return safeApiCall { RetrofitInstance.api.getUser(email, password) }
    }

    suspend fun getOrderByStatus(status: String): SimpleResponse<List<Order>> {
        return safeApiCall { RetrofitInstance.api.getOrderByStatus(status, "timestamp", "desc") }
    }

    suspend fun postUpdateOrderStatus(id: Int, order: Order): SimpleResponse<Order> {
        return safeApiCall { RetrofitInstance.api.updateOrderStatus(id, order) }
    }

    private inline fun <T> safeApiCall(apiCall: () -> Response<T>): SimpleResponse<T> {
        return try {
            SimpleResponse.success(apiCall.invoke())
        } catch (ex: Exception) {
            SimpleResponse.failure(ex)
        }
    }
}