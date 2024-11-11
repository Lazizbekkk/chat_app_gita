package com.example.mamadiyorov_lazizbek.chatappgita.viewmodels.chats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mamadiyorov_lazizbek.chatappgita.data.sourse.data.MessageData
import com.example.mamadiyorov_lazizbek.chatappgita.data.sourse.data.UserData
import com.example.mamadiyorov_lazizbek.chatappgita.repository.ChatRepository
import com.example.mamadiyorov_lazizbek.chatappgita.repository.ChatRepositoryImpl
import com.example.mamadiyorov_lazizbek.chatappgita.repository.MessagesCallback

class ChatsViewModelImpl(private val kimdanUserId: String, private val kimgaUserId: String) :
    ChatsViewModel, ViewModel() {
    private val repository: ChatRepository = ChatRepositoryImpl(kimdanUserId, kimgaUserId)
    override val showUsersList: MutableLiveData<List<UserData>> = MutableLiveData()
    val showMessagesList: MutableLiveData<List<MessageData>> = MutableLiveData()
    override val error: MutableLiveData<String> = MutableLiveData()

    override fun getAllChats() {
        repository.getMessages(
            currentUserId = kimdanUserId,
            otherUserId = kimgaUserId,
            callback = object : MessagesCallback {
                override fun onMessagesLoaded(messages: List<MessageData>) {
                    showMessagesList.postValue(messages)  // LiveData ga qiymat berish
                }

                override fun onError(exception: Exception) {
                    error.postValue(exception.message)
                }
            }
        )
    }



}