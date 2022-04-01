package com.example.smartkitchenandroid.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartkitchenandroid.models.Order
import com.example.smartkitchenandroid.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class WaiterViewModel(private val repository: Repository) : ViewModel() {
    val apiResponse: MutableLiveData<Response<Array<Order>>> = MutableLiveData()
    val statusCode: MutableLiveData<Response<String>> = MutableLiveData()

    fun getOrderByStatus(status: String) {
        viewModelScope.launch {
            val response = repository.getOrderByStatus(status)
            apiResponse.value = response
        }
    }

    fun updateOrderStatus(status: String) {
        viewModelScope.launch {
            val response = repository.postUpdateOrderStatus(status)
            statusCode.value = response
        }
    }
}