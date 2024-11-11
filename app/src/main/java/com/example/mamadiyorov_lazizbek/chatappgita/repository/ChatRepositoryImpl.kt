package com.example.mamadiyorov_lazizbek.chatappgita.repository

import androidx.lifecycle.MutableLiveData
import com.example.mamadiyorov_lazizbek.chatappgita.data.sourse.data.MessageData
import com.example.mamadiyorov_lazizbek.chatappgita.utils.constants.AppConstants
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class ChatRepositoryImpl(val kimdanUserId: String,val kimgaUserId: String) : ChatRepository {

    private var messagesListener: ListenerRegistration? = null

    override val message = MutableLiveData<List<MessageData>>()


    private val db = Firebase.firestore




    override fun getMessages(
        fromUserId: String,
        toUserId: String,
        onMessagesChanged: (List<MessageData>) -> Unit
    ) {
        db.collection("chats")
//            .whereEqualTo(AppConstants.KIMDAN_USER_ID, fromUserId)
//            .whereEqualTo(KIMGA_USER_ID, toUserId)
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    exception.printStackTrace()
                    return@addSnapshotListener
                }

                if (snapshot != null && !snapshot.isEmpty) {
                    val messages = snapshot.documents.map { document ->
                        document.toObject(MessageData::class.java)!!
                    }
                    onMessagesChanged(messages)
                } else {
                    onMessagesChanged(emptyList())
                }
            }
    }

    override fun sendMessage(
        messageData: MessageData,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val map = mapOf(
            "message" to messageData.message,
            AppConstants.KIMGA_USER_ID to messageData.kimgaUserId,
            AppConstants.KIMDAN_USER_ID to messageData.kimdanUserId,
            AppConstants.SENT_TIME to messageData.sentTime)
        db.collection("chats")
            .add(map)
            .addOnSuccessListener {
                onSuccess.invoke()
//            getMessages(kimdanUserId,kimgaUserId,)}
            }
            .addOnFailureListener{onFailure.invoke(it.message.toString())}
    }

}
