package com.example.mamadiyorov_lazizbek.chatappgita.repository

interface ChatRepository {
    fun getMessages(currentUserId: String, otherUserId: String, callback: MessagesCallback)
}