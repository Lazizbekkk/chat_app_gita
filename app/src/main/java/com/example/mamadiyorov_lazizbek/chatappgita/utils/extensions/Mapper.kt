package com.example.mamadiyorov_lazizbek.chatappgita.utils.extensions

import com.example.mamadiyorov_lazizbek.chatappgita.data.sourse.data.MessageData
import com.google.firebase.firestore.QueryDocumentSnapshot

fun QueryDocumentSnapshot.toMessage(): MessageData {
    val data = MessageData(
        messageId = id,
        message = get(MessageData::message.name).toString(),
        kimdanUserId = get((MessageData::kimdanUserId.name)).toString(),
        kimgaUserId = get(MessageData::kimgaUserId.name).toString(),
        sentTime = get(MessageData::sentTime.name).toString()
    )
    return data
}