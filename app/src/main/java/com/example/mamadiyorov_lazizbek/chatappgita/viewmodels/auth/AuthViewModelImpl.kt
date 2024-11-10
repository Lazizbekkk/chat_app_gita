package com.example.mamadiyorov_lazizbek.chatappgita.viewmodels.auth

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mamadiyorov_lazizbek.chatappgita.repository.AppRepository
import com.example.mamadiyorov_lazizbek.chatappgita.repository.AppRepositoryImpl

class AuthViewModelImpl : AuthViewModel, ViewModel() {
    private val repository: AppRepository = AppRepositoryImpl()

    override val setEnabledEnterButton: MutableLiveData<Boolean> = MutableLiveData()
    override val inputUserNickName: MutableLiveData<String> = MutableLiveData()
    override val openUserAuthenticated: MutableLiveData<Unit> = MutableLiveData()
    override fun openUserAuthenticated() {
        openUserAuthenticated.value = Unit
    }

    override val error: MutableLiveData<String> = MutableLiveData()

    override fun inputUserNickName(inputUserName: String) {
        this.inputUserNickName.value = inputUserName
        setEnabledEnterButton.value = inputUserName.isNotEmpty()
    }

    override fun firebaseAuthWithGoogle(idToken: String) {
        repository.firebaseAuthWithGoogle(
            idToken = idToken,
            onUserAuthenticated = {
                openUserAuthenticated.postValue(Unit)  // LiveData yangilash
                Log.d("BBBBB", "open next screen")
            },
            onAuthFailed = {
                error.postValue(it)  // Xatolikni uzatish
                Log.d("BBBBB", "open fail")
            }
        )
    }
}
