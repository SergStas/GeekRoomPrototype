package com.example.domain.repo

import com.example.domain.models.AuthData
import com.example.domain.models.UserData
import com.example.domain.usecases.login.models.LoginResult
import com.example.domain.usecases.register.models.RegistrationResult

interface IAuthRepo {
    suspend fun register(registrationData: AuthData): RegistrationResult

    suspend fun login(data: AuthData): LoginResult

    suspend fun getUser(): UserData?

    suspend fun logout()
}