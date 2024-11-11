package com.example.mamadiyorov_lazizbek.chatappgita.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.mamadiyorov_lazizbek.chatappgita.data.sourse.data.MessageData
import com.example.mamadiyorov_lazizbek.chatappgita.data.sourse.data.UserData
import com.example.mamadiyorov_lazizbek.chatappgita.utils.constants.AppConstants
import com.example.mamadiyorov_lazizbek.chatappgita.utils.constants.AppConstants.KIMGA_USER_ID
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.firestore

class AppRepositoryImpl(userID: String) : AppRepository {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()  // FirebaseAuth obyektini yaratish
    override val usersLiveData: MutableLiveData<List<UserData>> = MutableLiveData()
    private val firebase = FirebaseFirestore.getInstance()

    init {
        getAllUsers(userID,
            onSuccsess = { users ->
                usersLiveData.value = users
            },
            onFailure = { exception ->
                Log.e("UserViewModel", "Error getting users: ${exception.message}")
            }
        )
    }

    override fun firebaseAuthWithGoogle(
        idToken: String,
        onUserAuthenticated: (() -> Unit)?,
        onAuthFailed: ((String) -> Unit)?
    ) {
        if (idToken.isNotEmpty()) {
            Log.d("KKKKKK", "Kelgan token: $idToken")
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        onUserAuthenticated?.invoke()
                    } else {
                        onAuthFailed?.invoke("Sizda kuzda tutilmagan xatolik yuz berdi")
                    }
                }
        } else {
            Log.d("KKKKKK", "Kelgan token: $idToken")
            onAuthFailed?.invoke("Token topilmadi")
        }
    }

    override fun getAllUsers(
        userID: String,
        onSuccsess: (List<UserData>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        firebase.collection(AppConstants.USER_DB_NAME)

            .get()
            .addOnSuccessListener { querySnapshot ->
                val users = querySnapshot.toObjects(UserData::class.java)
                usersLiveData.value = users
                onSuccsess.invoke(users)
            }
            .addOnFailureListener { exception ->
                onFailure.invoke(exception)
            }
    }
    private var messagesListener: ListenerRegistration? = null

    private val db = Firebase.firestore

    override fun getMessages(fromUserId: String, toUserId: String, onMessagesChanged: (List<MessageData>) -> Unit) {

        db.collection("chats")
            .whereEqualTo(AppConstants.KIMDAN_USER_ID, fromUserId)
            .whereEqualTo(KIMGA_USER_ID, toUserId)
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
}
