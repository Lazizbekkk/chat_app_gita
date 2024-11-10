package com.example.mamadiyorov_lazizbek.chatappgita.viewmodels.user_list
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mamadiyorov_lazizbek.chatappgita.data.sourse.data.UserData
import com.example.mamadiyorov_lazizbek.chatappgita.repository.AppRepository
import com.example.mamadiyorov_lazizbek.chatappgita.repository.AppRepositoryImpl

class UsersListViewModelImpl(private val userId: String) : UsersListViewModel, ViewModel() {
    private val repository: AppRepository = AppRepositoryImpl(userId)
    override val openChatScreen: MutableLiveData<Unit> = MutableLiveData()
    override val showUsersList: MutableLiveData<List<UserData>> = MutableLiveData()



    override val error: MutableLiveData<String> = MutableLiveData()

    override fun getAllUsers() {
      repository.getAllUsers(
          userID = userId,
          onSuccsess = {
            showUsersList.value = it

          },
          onFailure = {
            error.value = it.toString()
          }

      )
    }

    override fun openChatScreen() {

    }

}