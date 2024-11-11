package com.example.mamadiyorov_lazizbek.chatappgita.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mamadiyorov_lazizbek.chatappgita.R
import com.example.mamadiyorov_lazizbek.chatappgita.data.sourse.data.UserData
import com.example.mamadiyorov_lazizbek.chatappgita.databinding.ItemUsersBinding
import de.hdodenhof.circleimageview.CircleImageView

class UserAdapter: ListAdapter<UserData, UserAdapter.UserVH>(diffutil) {
    private var moveUserClicked: ( (UserData) -> Unit) ?= null


    object diffutil: DiffUtil.ItemCallback<UserData>() {
        override fun areItemsTheSame(oldItem: UserData, newItem: UserData): Boolean {
            return oldItem.userId == newItem.userId
        }

        override fun areContentsTheSame(oldItem: UserData, newItem: UserData): Boolean {
           return oldItem == newItem
        }
    }

    inner class UserVH(private val binding: ItemUsersBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                moveUserClicked?.invoke(currentList[adapterPosition])
            }
        }
        fun bind(){
            binding.userName.text = currentList[adapterPosition].userName
            loadUserProfileImage(currentList[adapterPosition].userProfileImage, binding.profileImage)

        }
        private fun loadUserProfileImage(imageUrl: String, imageView: CircleImageView) {
            Glide.with(imageView.context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_def_contact) // rasm yuklanayotganda ko'rsatiladigan tasvir
                .error(R.drawable.ic_def_contact) // rasm yuklanishda xato bo'lsa ko'rsatiladigan tasvir
                .into(imageView)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserVH =
        UserVH(ItemUsersBinding.inflate(LayoutInflater.from(parent.context),parent, false))

    override fun onBindViewHolder(holder: UserVH, position: Int) {
        holder.bind()
    }

    fun setMoveUserClicked(l : ((UserData) -> Unit)){
        moveUserClicked = l
    }
}