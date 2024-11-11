package com.example.mamadiyorov_lazizbek.chatappgita.repository

import com.example.mamadiyorov_lazizbek.chatappgita.data.sourse.data.MessageData

interface MessagesCallback {
    fun onMessagesLoaded(messages: List<MessageData>)
    fun onError(exception: Exception)
}
