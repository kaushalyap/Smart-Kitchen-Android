package com.example.smartkitchenandroid.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartkitchenandroid.models.User
import com.example.smartkitchenandroid.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class SignInViewModel(private val repository: Repository) : ViewModel() {
    val apiResponse: MutableLiveData<Response<Array<User>>> = MutableLiveData()
    fun getUser(user: User) {

        viewModelScope.launch {
            val response = repository.getUser(user.email, "asf145ksapgkbak")
            apiResponse.value = response
        }
    }
}