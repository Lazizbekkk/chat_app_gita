package com.example.mamadiyorov_lazizbek.chatappgita.data.sourse.data

import java.io.Serializable

data class MessageData(
    val messageId: String = "",
    val message: String = "",
    val kimdanUserId: String = "",
    val kimgaUserId: String = "",
    val sentTime: String = ""
): Serializable