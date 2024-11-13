package com.example.mamadiyorov_lazizbek.chatappgita.viewmodels.chats

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mamadiyorov_lazizbek.chatappgita.data.sourse.data.MessageData
import com.example.mamadiyorov_lazizbek.chatappgita.repository.ChatRepository
import com.example.mamadiyorov_lazizbek.chatappgita.repository.ChatRepositoryImpl

class ChatsViewModelImpl(private val kimdanUserId: String, private val kimgaUserId: String) :
    ChatsViewModel, ViewModel() {
    private val repository: ChatRepository = ChatRepositoryImpl(kimdanUserId, kimgaUserId)
    override val error: MutableLiveData<String> = MutableLiveData()
    override val showMessages: MutableLiveData<List<MessageData>> = MutableLiveData()

    override val sendMessage: MutableLiveData<String> = MutableLiveData()

    override fun sendMessage(messageData: MessageData) {
        if(sendMessage.value != messageData.message){
            repository.sendMessage(
                messageData,
                onSuccess = {
                    sendMessage.value = messageData.message
                },
                onFailure = {
                    error.value = it.toString()
                }
            )
        }
    }

    override fun getAllChats() {
        repository.getMessages(
            fromUserId = kimdanUserId,
            toUserId = kimgaUserId
        ) {
            showMessages.value = it
        }
    }


}