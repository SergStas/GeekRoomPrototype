package com.example.domain.usecases.login

import com.example.domain.usecases.login.models.LoginArgs
import com.example.domain.usecases.login.models.LoginResult

interface ILoginUseCase {
    suspend operator fun invoke(args: LoginArgs): LoginResult
}