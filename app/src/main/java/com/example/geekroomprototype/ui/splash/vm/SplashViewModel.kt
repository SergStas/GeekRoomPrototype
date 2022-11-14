package com.example.geekroomprototype.ui.splash.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.AuthData
import com.example.domain.models.UserData
import com.example.domain.usecases.login.IGetLoggedInUserUseCase
import com.example.domain.usecases.login.ILoginUseCase
import com.example.domain.usecases.login.models.LoginArgs
import com.example.domain.usecases.login.models.LoginResult
import com.example.domain.usecases.register.IRegistrationUseCase
import com.example.domain.usecases.register.models.RegistrationArgs
import com.example.domain.usecases.register.models.RegistrationResult
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val loginUseCase: ILoginUseCase,
    private val registerUseCase: IRegistrationUseCase,
    private val getUserUseCase: IGetLoggedInUserUseCase,
): ViewModel() {
    private val _user = MutableLiveData<UserData?>(null)
    val user: LiveData<UserData?> get() = _user

    private val _errMsg = MutableLiveData<String?>(null)
    val errMsg: LiveData<String?> get() = _errMsg

    fun loadUser() {
        viewModelScope.launch {
            _user.value = getUserUseCase()
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            when (val result = loginUseCase(LoginArgs(AuthData(username, password)))) {
                is LoginResult.Error ->
                    _errMsg.value = result.msg
                LoginResult.Success ->
                    loadUser()
            }
        }
    }

    fun register(username: String, password: String) {
        viewModelScope.launch {
            when (val result = registerUseCase(RegistrationArgs(AuthData(username, password)))) {
                is RegistrationResult.Error ->
                    _errMsg.value = result.msg
                RegistrationResult.Success ->
                    loadUser()
            }
        }
    }
}