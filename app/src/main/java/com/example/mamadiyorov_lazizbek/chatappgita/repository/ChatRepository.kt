package com.example.mamadiyorov_lazizbek.chatappgita.repository

import androidx.lifecycle.LiveData
import com.example.mamadiyorov_lazizbek.chatappgita.data.sourse.data.MessageData

interface ChatRepository {
    val message:LiveData<List<MessageData>>

    fun getMessages(fromUserId: String, toUserId: String, onMessagesChanged: (List<MessageData>) -> Unit)
     fun sendMessage(messageData: MessageData,onSuccess:()->Unit,onFailure:(String)->Unit)
}