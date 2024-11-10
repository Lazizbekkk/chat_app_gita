package com.example.mamadiyorov_lazizbek.chatappgita.viewmodels.user_list

import androidx.lifecycle.LiveData
import com.example.mamadiyorov_lazizbek.chatappgita.data.sourse.data.UserData

interface UsersListViewModel {
    val openChatScreen: LiveData<Unit>
    val showUsersList: LiveData<List<UserData>>
    val error: LiveData<String>

    fun getAllUsers()
    fun openChatScreen()
}