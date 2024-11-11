package com.example.mamadiyorov_lazizbek.chatappgita.repository

import androidx.lifecycle.LiveData
import com.example.mamadiyorov_lazizbek.chatappgita.data.sourse.data.MessageData
import com.google.android.gms.tasks.OnFailureListener

interface ChatRepository {
    val message:LiveData<List<MessageData>>
    fun getMessage(currentUserId: String, otherUserId: String, callback: MessagesCallback)
    fun getMessages(fromUserId: String, toUserId: String, onMessagesChanged: (List<MessageData>) -> Unit)
     fun sendMessage(message:String,onSuccess:()->Unit,onFailure:(String)->Unit)
}