package com.example.mamadiyorov_lazizbek.chatappgita.viewmodels.chats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ChatsListViewModelFactory(private val kimdanUserId: String, private val kimgaUserId: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatsViewModelImpl::class.java)) {
            return ChatsViewModelImpl(kimdanUserId, kimgaUserId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}