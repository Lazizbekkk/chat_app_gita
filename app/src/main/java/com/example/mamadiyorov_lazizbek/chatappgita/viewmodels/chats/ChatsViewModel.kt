package com.example.mamadiyorov_lazizbek.chatappgita.viewmodels.chats

import androidx.lifecycle.LiveData
import com.example.mamadiyorov_lazizbek.chatappgita.data.sourse.data.MessageData

interface ChatsViewModel {
   fun getAllChats()
   val error: LiveData<String>
   val showMessages: LiveData<List<MessageData>>

   val sendMessage: LiveData<String>
   fun sendMessage(messageData: MessageData)

}