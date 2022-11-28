package com.example.geekroomprototype.ui.auth.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.AuthData
import com.example.domain.models.UserData
import com.example.domain.usecases.login.GetLoggedInUserUseCase
import com.example.domain.usecases.login.LoginUseCase
import com.example.domain.usecases.login.models.LoginArgs
import com.example.domain.usecases.login.models.LoginResult
import com.example.domain.usecases.register.RegistrationUseCase
import com.example.domain.usecases.register.models.RegistrationArgs
import com.example.domain.usecases.register.models.RegistrationResult
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegistrationUseCase,
    private val getUserUseCase: GetLoggedInUserUseCase,
): ViewModel() {
    private val _state = MutableLiveData<State>(State.No)
    val state: LiveData<State> get() = _state

    fun loadUser() {
        viewModelScope.launch {
            _state.value = getUserUseCase()?.run { State.Yes(this) } ?: State.No
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            when (val result = loginUseCase(LoginArgs(AuthData(username, password)))) {
                is LoginResult.Error ->
                    _state.value = State.Error(result.msg)
                LoginResult.Success ->
                    loadUser()
            }
        }
    }

    fun register(username: String, password: String) {
        viewModelScope.launch {
            when (val result = registerUseCase(RegistrationArgs(AuthData(username, password)))) {
                is RegistrationResult.Error -> {
                    val prevState = _state.value!!
                    _state.value = State.Error(result.msg)
                    _state.value = prevState
                }
                RegistrationResult.Success ->
                    loadUser()
            }
        }
    }

    sealed class State {
        object No: State()
        data class Error(val msg: String): State()
        data class Yes(val user: UserData): State()
    }
}