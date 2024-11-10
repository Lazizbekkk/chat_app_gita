package com.example.mamadiyorov_lazizbek.chatappgita.repository

interface AppRepository {

    fun firebaseAuthWithGoogle(idToken: String, onUserAuthenticated: (() -> Unit)?, onAuthFailed: ((String) -> Unit)?)

}