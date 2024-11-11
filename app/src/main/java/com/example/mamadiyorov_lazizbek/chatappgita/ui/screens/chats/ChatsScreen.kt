package com.example.mamadiyorov_lazizbek.chatappgita.ui.screens.chats

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mamadiyorov_lazizbek.chatappgita.R
import com.example.mamadiyorov_lazizbek.chatappgita.databinding.ScreenChatsBinding
import com.example.mamadiyorov_lazizbek.chatappgita.ui.adapters.ChatAdapter
import com.example.mamadiyorov_lazizbek.chatappgita.ui.screens.user_list.ScreenUserListArgs

class ChatsScreen: Fragment(R.layout.screen_chats) {
     private val binding : ScreenChatsBinding by viewBinding(ScreenChatsBinding::bind)
    private val args : ScreenUserListArgs by navArgs()
  //  private val adapter: ChatAdapter by lazy { ChatAdapter(args.) }

}