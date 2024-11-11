package com.example.mamadiyorov_lazizbek.chatappgita.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mamadiyorov_lazizbek.chatappgita.R
import com.example.mamadiyorov_lazizbek.chatappgita.data.sourse.data.MessageData

class ChatAdapter(private val currentUserId: String) :
    ListAdapter<MessageData, RecyclerView.ViewHolder>(MessageDiffCallback) {

    private val VIEW_TYPE_SENT = 1
    private val VIEW_TYPE_RECEIVED = 2

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).kimdanUserId == currentUserId) VIEW_TYPE_SENT else VIEW_TYPE_RECEIVED
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_SENT) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_sent, parent, false)
            SentMessageViewHolder(view)
        } else {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_come, parent, false)
            ReceivedMessageViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = getItem(position)
        if (holder is SentMessageViewHolder) {
            holder.bind(message)
        } else if (holder is ReceivedMessageViewHolder) {
            holder.bind(message)
        }
    }

    inner class SentMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val sentMessageTextView: TextView = itemView.findViewById(R.id.send_text)

        fun bind(message: MessageData) {
            sentMessageTextView.text = message.message
        }
    }

    inner class ReceivedMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val receivedMessageTextView: TextView = itemView.findViewById(R.id.come_text)

        fun bind(message: MessageData) {
            receivedMessageTextView.text = message.message
        }
    }


    object MessageDiffCallback : DiffUtil.ItemCallback<MessageData>() {
        override fun areItemsTheSame(oldItem: MessageData, newItem: MessageData): Boolean {
            return oldItem.messageId == newItem.messageId
        }

        override fun areContentsTheSame(oldItem: MessageData, newItem: MessageData): Boolean {
            return oldItem == newItem
        }
    }
}