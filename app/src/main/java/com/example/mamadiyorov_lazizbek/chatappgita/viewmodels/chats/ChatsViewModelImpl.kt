package com.example.mamadiyorov_lazizbek.chatappgita.viewmodels.chats

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mamadiyorov_lazizbek.chatappgita.data.sourse.data.MessageData
import com.example.mamadiyorov_lazizbek.chatappgita.data.sourse.data.UserData
import com.example.mamadiyorov_lazizbek.chatappgita.repository.ChatRepository
import com.example.mamadiyorov_lazizbek.chatappgita.repository.ChatRepositoryImpl

class ChatsViewModelImpl(private val kimdanUserId: String, private val kimgaUserId: String) :
    ChatsViewModel, ViewModel() {
    private val repository: ChatRepository = ChatRepositoryImpl(kimdanUserId, kimgaUserId)
    override val showUsersList: MutableLiveData<List<UserData>> = MutableLiveData()
    val showMessagesList: MutableLiveData<List<MessageData>> = MutableLiveData()
    override val error: MutableLiveData<String> = MutableLiveData()

    override fun getAllChats() {
        repository.getMessages(
            fromUserId = kimdanUserId,
            toUserId = kimgaUserId
        ) {
            showMessagesList.value = it
        }
    }


}