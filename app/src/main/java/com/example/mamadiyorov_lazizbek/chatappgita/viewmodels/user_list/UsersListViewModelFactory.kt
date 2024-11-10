package com.example.mamadiyorov_lazizbek.chatappgita.viewmodels.user_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class UsersListViewModelFactory(private val userId: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsersListViewModelImpl::class.java)) {
            return UsersListViewModelImpl(userId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}
