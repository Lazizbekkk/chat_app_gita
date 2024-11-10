package com.example.mamadiyorov_lazizbek.chatappgita.viewmodels.auth

import androidx.lifecycle.LiveData

interface AuthViewModel {
    val setEnabledEnterButton: LiveData<Boolean>
    val inputUserNickName: LiveData<String>

    fun inputUserNickName(inputUserName: String)
    fun firebaseAuthWithGoogle(idToken: String)

    val openUserAuthenticated : LiveData<Unit>
    fun openUserAuthenticated()
    val error: LiveData<String>

}