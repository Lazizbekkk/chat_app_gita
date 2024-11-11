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

    override fun getMessage(
        currentUserId: String,
        otherUserId: String,
        callback: MessagesCallback
    ) {
        val db = FirebaseFirestore.getInstance()
        val messages = mutableListOf<MessageData>()

        val query1 = db.collection("chats")
            .whereEqualTo(AppConstants.KIMDAN_USER_ID, currentUserId)
            .whereEqualTo(AppConstants.KIMGA_USER_ID, otherUserId)
            .get()

        val query2 = db.collection("chats")
            .whereEqualTo(AppConstants.KIMDAN_USER_ID, otherUserId)
            .whereEqualTo(AppConstants.KIMGA_USER_ID, currentUserId)
            .get()

        query1.addOnSuccessListener { result1 ->
            for (document in result1.documents) {
                val message = document.toObject(MessageData::class.java)
                message?.let { messages.add(it) }
            }

            query2.addOnSuccessListener { result2 ->
                for (document in result2.documents) {
                    val message = document.toObject(MessageData::class.java)
                    message?.let { messages.add(it) }
                }

                messages.sortBy { it.sentTime }
                callback.onMessagesLoaded(messages)  // muvaffaqiyatli olingan natijalarni qaytarish

            }.addOnFailureListener { exception ->
                callback.onError(exception)  // xatolik yuzaga kelsa, callback yordamida yuborish
            }

        }.addOnFailureListener { exception ->
            callback.onError(exception)  // query1 xato bo'lsa, callback yordamida xabar yuborish
        }
    }


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
        message: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val map = mapOf("message" to message)
        db.collection("chats")
            .add(map)
            .addOnSuccessListener {
                onSuccess.invoke()
//            getMessages(kimdanUserId,kimgaUserId,)}
            }
            .addOnFailureListener{onFailure.invoke(it.message.toString())}
    }

}
