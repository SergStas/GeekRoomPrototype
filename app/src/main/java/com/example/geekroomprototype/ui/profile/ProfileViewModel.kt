package com.example.geekroomprototype.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.UserData
import com.example.domain.usecases.login.GetLoggedInUserUseCase
import com.example.domain.usecases.logout.LogoutUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val getLoggedInUser: GetLoggedInUserUseCase,
    private val logoutUc: LogoutUseCase,
): ViewModel() {
    private val _userData = MutableLiveData<State>(State.Loading)
    val userData: LiveData<State> get() = _userData

    fun loadUser() {
        viewModelScope.launch {
            val user = getLoggedInUser()
            _userData.value = user?.run {
                State.LoggedIn(this)
            } ?: State.LoggedOut
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUc()
            loadUser()
        }
    }

    sealed class State {
        object Loading: State()
        data class LoggedIn(val userData: UserData): State()
        object LoggedOut: State()
    }
}