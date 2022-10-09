package com.example.domain.usecases.register.models

sealed class RegistrationResult {
    object Success: RegistrationResult()

    data class Error(val msg: String): RegistrationResult()
}