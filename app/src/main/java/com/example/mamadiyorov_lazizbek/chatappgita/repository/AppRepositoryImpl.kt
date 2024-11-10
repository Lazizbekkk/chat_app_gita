package com.example.mamadiyorov_lazizbek.chatappgita.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class AppRepositoryImpl: AppRepository {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()  // FirebaseAuth obyektini yaratish

    override fun firebaseAuthWithGoogle(idToken: String, onUserAuthenticated: (() -> Unit)?, onAuthFailed: ((String) -> Unit)?) {
        if (idToken.isNotEmpty()) {
            Log.d("KKKKKK", "Kelgan token: $idToken")
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Agar autentifikatsiya muvaffaqiyatli bo'lsa, userni autentifikatsiya qilishni kutib turing
                        onUserAuthenticated?.invoke()
                    } else {
                        // Agar autentifikatsiya muvaffaqiyatli bo'lmasa, xatolikni ko'rsating
                        onAuthFailed?.invoke("Sizda kuzda tutilmagan xatolik yuz berdi")
                    }
                }
        } else {
            Log.d("KKKKKK", "Kelgan token: $idToken")
            // Agar idToken bo'sh bo'lsa, xatolik qaytarish
            onAuthFailed?.invoke("Token topilmadi")
        }
    }
}