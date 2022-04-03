package com.example.smartkitchenandroid.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartkitchenandroid.models.Order
import com.example.smartkitchenandroid.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class WaiterViewModel(private val repository: Repository) : ViewModel() {
    val apiResponse: MutableLiveData<Response<List<Order>>> = MutableLiveData()
    val error: MutableLiveData<String> = MutableLiveData()
    val statusCode: MutableLiveData<Response<String>> = MutableLiveData()

    fun getOrderByStatus(status: String) {
        viewModelScope.launch {
            try {
                val response = repository.getOrderByStatus(status)
                apiResponse.value = response

            } catch (ex: Exception) {
                error.value = ex.message.toString()
//                Log.d("WaiterViewModel", ex.stackTraceToString())
            }
        }
    }

    fun updateOrderStatus(status: String) {
        viewModelScope.launch {
            try {
                val response = repository.postUpdateOrderStatus(status)
                statusCode.value = response
            } catch (ex: Exception) {
                error.value = ex.message.toString()
//                Log.d("WaiterViewModel", ex.message.toString())
            }
        }
    }

    companion object {
        const val TAG: String = "WaiterViewModel"
    }
}