package com.example.smartkitchenandroid.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartkitchenandroid.models.User
import com.example.smartkitchenandroid.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class KCoordinatorViewModel(private val repository: Repository) : ViewModel() {
    private val apiResponse: MutableLiveData<Response<Array<User>>> = MutableLiveData()
    fun getUser() {
        viewModelScope.launch {
            val response = repository.getUser("john@example.com", "asf145ksapgkbak")
            apiResponse.value = response
        }
    }
}