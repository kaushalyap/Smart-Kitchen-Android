package com.example.smartkitchenandroid.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartkitchenandroid.api.SimpleResponse
import com.example.smartkitchenandroid.models.Order
import com.example.smartkitchenandroid.repository.Repository
import kotlinx.coroutines.launch

class WaiterViewModel(private val repository: Repository) : ViewModel() {
    val apiResponse: MutableLiveData<SimpleResponse<List<Order>>> = MutableLiveData()
    val statusCode: MutableLiveData<SimpleResponse<String>> = MutableLiveData()
    val error: MutableLiveData<String> = MutableLiveData()

    fun getOrderByStatus(status: String) {
        viewModelScope.launch {
            try {
                val response = repository.getOrderByStatus(status)
                apiResponse.value = response
            } catch (ex: Exception) {
                error.value = ex.stackTraceToString()
            }
        }
    }

    fun updateOrderStatus(status: String) {
        viewModelScope.launch {
            try {
                val response = repository.postUpdateOrderStatus(status)
                statusCode.value = response
            } catch (ex: Exception) {
                Log.d("WaiterViewModel", ex.message.toString())
            }
        }
    }

    companion object {
        const val TAG: String = "WaiterViewModel"
    }
}