package com.example.mamadiyorov_lazizbek.chatappgita.data.sourse.data

data class MessageData(
    val messageId: String,
    val message: String,
    val fromUserId: String,
    val toUserId: String,
    val sentTime: String
    // fromUserId -> toUserId   kimdan -> kimga xabar boryabdi shu maqsadda yozildi
)