package com.example.smartkitchenandroid.viewmodels

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartkitchenandroid.api.SimpleResponse
import com.example.smartkitchenandroid.models.User
import com.example.smartkitchenandroid.repository.Repository
import kotlinx.coroutines.launch

@SuppressLint("LogConditional")
class SignInViewModel(private val repository: Repository) : ViewModel() {
    val apiResponse: MutableLiveData<SimpleResponse<Array<User>>> = MutableLiveData()

    fun getUser(user: User) {
        viewModelScope.launch {
            try {
                val response = repository.getUser(user.email, user.password)
                apiResponse.value = response
            } catch (ex: Exception) {
                Log.d(TAG, ex.message.toString())
            }
        }
    }

    companion object {
        const val TAG: String = "SignInViewModel"
    }
}