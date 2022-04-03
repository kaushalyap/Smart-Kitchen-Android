package com.example.smartkitchenandroid.viewmodels

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartkitchenandroid.models.User
import com.example.smartkitchenandroid.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

@SuppressLint("LogConditional")
class SignInViewModel(private val repository: Repository) : ViewModel() {
    val apiResponse: MutableLiveData<Response<Array<User>>> = MutableLiveData()
    val error: MutableLiveData<String> = MutableLiveData()

    fun getUser(user: User) {
        viewModelScope.launch {
            try {
                val response = repository.getUser(user.email, "asf145ksapgkbak")
                apiResponse.value = response
            } catch (ex: Exception) {
                error.value = ex.stackTraceToString()
//                Log.d(TAG, ex.message.toString())
            }
        }
    }

    companion object {
        const val TAG: String = "SignInViewModel"
    }
}