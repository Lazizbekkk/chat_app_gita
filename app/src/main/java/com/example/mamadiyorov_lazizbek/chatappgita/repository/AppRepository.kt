package com.example.mamadiyorov_lazizbek.chatappgita.repository

import androidx.lifecycle.LiveData
import com.example.mamadiyorov_lazizbek.chatappgita.data.sourse.data.UserData

interface AppRepository {
    val usersLiveData: LiveData<List<UserData>>

    fun firebaseAuthWithGoogle(idToken: String, onUserAuthenticated: (() -> Unit)?, onAuthFailed: ((String) -> Unit)?)
    fun getAllUsers(userID: String, onSuccsess: ((List<UserData>) -> Unit), onFailure: ((Exception) -> Unit) )

}