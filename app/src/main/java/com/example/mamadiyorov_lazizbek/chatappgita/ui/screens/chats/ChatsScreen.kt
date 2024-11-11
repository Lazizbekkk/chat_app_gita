package com.example.mamadiyorov_lazizbek.chatappgita.ui.screens.chats

import android.os.Bundle
import android.os.SystemClock
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.mamadiyorov_lazizbek.chatappgita.R
import com.example.mamadiyorov_lazizbek.chatappgita.data.sourse.data.MessageData
import com.example.mamadiyorov_lazizbek.chatappgita.databinding.ScreenChatsBinding
import com.example.mamadiyorov_lazizbek.chatappgita.ui.adapters.ChatAdapter
import com.example.mamadiyorov_lazizbek.chatappgita.viewmodels.chats.ChatsListViewModelFactory
import com.example.mamadiyorov_lazizbek.chatappgita.viewmodels.chats.ChatsViewModel
import com.example.mamadiyorov_lazizbek.chatappgita.viewmodels.chats.ChatsViewModelImpl
import com.google.android.gms.common.util.Clock
import de.hdodenhof.circleimageview.CircleImageView

class ChatsScreen : Fragment(R.layout.screen_chats) {
    private val binding: ScreenChatsBinding by viewBinding(ScreenChatsBinding::bind)
    private val args: ChatsScreenArgs by navArgs()
    private val adapter: ChatAdapter by lazy { ChatAdapter(args.userIdCurrent) }
    private val viewModel: ChatsViewModel by lazy {
        ViewModelProvider(this,
            ChatsListViewModelFactory(args.userIdCurrent, args.userIdOther)
        )[ChatsViewModelImpl::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadUserProfileImage(args.userProfileImageOther, binding.pofileImage)
        binding.textTitle.text = args.userNameNextOther

        viewModel.showMessages.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }

        viewModel.getAllChats()


        binding.recChats.adapter = adapter
        binding.recChats.layoutManager = LinearLayoutManager(requireContext())
        binding.buttonSend.setOnClickListener {
            viewModel.sendMessage(MessageData(SystemClock.elapsedRealtime().toString(),
                binding.inputMessage.text.toString(),
                kimdanUserId = args.userIdCurrent, kimgaUserId = args.userIdOther,
                SystemClock.elapsedRealtime().toString()))
            binding.inputMessage.setText("")
            viewModel.getAllChats()
        }

    }

    private fun loadUserProfileImage(imageUrl: String, imageView: CircleImageView) {
        Glide.with(imageView.context)
            .load(imageUrl)
            .placeholder(R.drawable.ic_def_contact) // rasm yuklanayotganda ko'rsatiladigan tasvir
            .error(R.drawable.ic_def_contact) // rasm yuklanishda xato bo'lsa ko'rsatiladigan tasvir
            .into(imageView)
    }

}