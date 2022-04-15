package com.example.smartkitchenandroid.repository

import com.example.smartkitchenandroid.api.RetrofitInstance
import com.example.smartkitchenandroid.api.SimpleResponse
import com.example.smartkitchenandroid.models.Order
import com.example.smartkitchenandroid.models.User
import retrofit2.Response

class Repository {

    suspend fun getUser(email: String, passwordHash: String): SimpleResponse<Array<User>> {
        return safeApiCall { RetrofitInstance.api.getUser(email, passwordHash) }
    }

    suspend fun getOrderByStatus(status: String): SimpleResponse<List<Order>> {
        return safeApiCall { RetrofitInstance.api.getOrderByStatus(status, "timestamp", "desc") }
    }

    suspend fun postUpdateOrderStatus(status: String): SimpleResponse<String> {
        return safeApiCall { RetrofitInstance.api.updateOrderStatus(status) }
    }

    private inline fun <T> safeApiCall(apiCall: () -> Response<T>): SimpleResponse<T> {
        return try {
            SimpleResponse.success(apiCall.invoke())
        } catch (ex: Exception) {
            SimpleResponse.failure(ex)
        }
    }
}