package com.example.mamadiyorov_lazizbek.chatappgita.viewmodels.chats

import androidx.lifecycle.LiveData
import com.example.mamadiyorov_lazizbek.chatappgita.data.sourse.data.UserData

interface ChatsViewModel {
   val showUsersList: LiveData<List<UserData>>
   fun getAllChats()
   val error: LiveData<String>

}