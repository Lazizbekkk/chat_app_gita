package com.example.mamadiyorov_lazizbek.chatappgita.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class AppRepositoryImpl: AppRepository {
    private val auth = FirebaseAuth.getInstance()

    // Google bilan kirish
    override fun firebaseAuthWithGoogle(idToken: String, onUserAuthenticated: (() -> Unit)?, onAuthFailed: ((String) -> Unit)?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                   onUserAuthenticated?.invoke()
                } else {
                    onAuthFailed?.invoke("Sizda kuzda tutilmagan xatolik yuz berdi")
                }
            }
    }
}