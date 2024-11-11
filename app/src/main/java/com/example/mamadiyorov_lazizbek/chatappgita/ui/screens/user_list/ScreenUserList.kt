package com.example.mamadiyorov_lazizbek.chatappgita.ui.screens.user_list

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mamadiyorov_lazizbek.chatappgita.R
import com.example.mamadiyorov_lazizbek.chatappgita.databinding.ScreenUserListBinding
import com.example.mamadiyorov_lazizbek.chatappgita.ui.adapters.UserAdapter
import com.example.mamadiyorov_lazizbek.chatappgita.viewmodels.user_list.UsersListViewModel
import com.example.mamadiyorov_lazizbek.chatappgita.viewmodels.user_list.UsersListViewModelFactory
import com.example.mamadiyorov_lazizbek.chatappgita.viewmodels.user_list.UsersListViewModelImpl

class ScreenUserList : Fragment(R.layout.screen_user_list) {
    private val binding: ScreenUserListBinding by viewBinding(ScreenUserListBinding::bind)
    private val args: ScreenUserListArgs by navArgs()
    private val userAdapter: UserAdapter by lazy { UserAdapter() }
    private val viewModel: UsersListViewModel by lazy {
        ViewModelProvider(
            this,
            UsersListViewModelFactory(args.userId)
        )[UsersListViewModelImpl::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("AAAAAA", "onCreated : args.userId  = ${args.userId} ")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("AAAAAA", "onViewCreated  ")
        viewModel.showUsersList.observe(viewLifecycleOwner) { it ->
            if (it != null) {
                Log.d("AAAAAA", "list keldi:  $it")
                userAdapter.submitList(it)
            } else {
                Log.d("AAAAAA", "Users list is null or empty")
            }
        }
        userAdapter.setMoveUserClicked {
            findNavController().navigate(
                ScreenUserListDirections.actionScreenUserListToChatsScreen(
                    userIdCurrent = args.userId,
                    userIdOther = it.userId,
                    userNameNextOther = it.userName,
                    userProfileImageOther = it.userProfileImage,
                    userEmailOther = it.userEmail,
                )
            )
        }


        viewModel.getAllUsers()
        binding.recList.adapter = userAdapter
        binding.recList.layoutManager = LinearLayoutManager(requireContext())
    }

}