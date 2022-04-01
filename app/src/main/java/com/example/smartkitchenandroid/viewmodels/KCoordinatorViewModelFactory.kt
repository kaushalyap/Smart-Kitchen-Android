package com.example.smartkitchenandroid.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartkitchenandroid.repository.Repository

class KCoordinatorViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WaiterViewModel(repository) as T
    }
}