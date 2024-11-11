package com.example.mamadiyorov_lazizbek.chatappgita.repository

import com.example.mamadiyorov_lazizbek.chatappgita.data.sourse.data.MessageData

interface ChatRepository {
    fun getMessage(currentUserId: String, otherUserId: String, callback: MessagesCallback)

    fun getMessages(fromUserId: String, toUserId: String, onMessagesChanged: (List<MessageData>) -> Unit)
}