package com.example.mamadiyorov_lazizbek.chatappgita.viewmodels.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mamadiyorov_lazizbek.chatappgita.repository.AppRepository
import com.example.mamadiyorov_lazizbek.chatappgita.repository.AppRepositoryImpl

class AuthViewModelImpl: AuthViewModel, ViewModel() {
    private val repository: AppRepository = AppRepositoryImpl()

    override val setEnabledEnterButton: MutableLiveData<Boolean> = MutableLiveData()
    override val inputUserNickName: MutableLiveData<String> = MutableLiveData()

    override fun inputUserNickName(inputUserName: String) {
        this.inputUserNickName.value = inputUserName
        setEnabledEnterButton.value = inputUserName.isNotEmpty()
    }

    override fun firebaseAuthWithGoogle(idToken: String) {
        repository.firebaseAuthWithGoogle(
            idToken = idToken,
            onUserAuthenticated = {
                openUserAuthenticated.value = Unit
            },
            onAuthFailed = {
                error.value = it
            }
        )
    }

    override val openUserAuthenticated: MutableLiveData<Unit> = MutableLiveData()
    override val error: MutableLiveData<String> = MutableLiveData()


}