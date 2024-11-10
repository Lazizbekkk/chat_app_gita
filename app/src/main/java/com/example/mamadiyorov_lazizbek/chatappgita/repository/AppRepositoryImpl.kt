package com.example.mamadiyorov_lazizbek.chatappgita.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.mamadiyorov_lazizbek.chatappgita.data.sourse.data.UserData
import com.example.mamadiyorov_lazizbek.chatappgita.utils.constants.AppConstants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore

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
}
