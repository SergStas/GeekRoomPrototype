package com.example.domain.usecases.login.models

sealed class LoginResult {
    object Success: LoginResult()

    data class Error(val msg: String): LoginResult()
}
