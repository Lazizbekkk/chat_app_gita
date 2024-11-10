package com.example.mamadiyorov_lazizbek.chatappgita.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mamadiyorov_lazizbek.chatappgita.data.sourse.data.UserData
import com.example.mamadiyorov_lazizbek.chatappgita.databinding.ItemUsersBinding

class UserAdapter: ListAdapter<UserData, UserAdapter.UserVH>(diffutil) {
    object diffutil: DiffUtil.ItemCallback<UserData>(

    ) {
        override fun areItemsTheSame(oldItem: UserData, newItem: UserData): Boolean {
            return oldItem.userId == newItem.userId
        }

        override fun areContentsTheSame(oldItem: UserData, newItem: UserData): Boolean {
           return oldItem == newItem
        }
    }

    inner class UserVH(private val binding: ItemUsersBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(){
            binding.userName.text = currentList[adapterPosition].userName
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserVH =
        UserVH(ItemUsersBinding.inflate(LayoutInflater.from(parent.context),parent, false))

    override fun onBindViewHolder(holder: UserVH, position: Int) {
        holder.bind()
    }
}