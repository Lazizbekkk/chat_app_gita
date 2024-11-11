package com.example.mamadiyorov_lazizbek.chatappgita.viewmodels.chats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mamadiyorov_lazizbek.chatappgita.repository.ChatRepositoryImpl
import com.example.mamadiyorov_lazizbek.chatappgita.viewmodels.user_list.UsersListViewModelImpl

class ChatsListViewModelFactory(private val kimdanUserId: String, private val kimgaUserId: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsersListViewModelImpl::class.java)) {
            return ChatRepositoryImpl(kimdanUserId, kimgaUserId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}