package com.example.domain.usecases.login

import com.example.domain.usecases.login.models.LoginUCArgs
import com.example.domain.usecases.login.models.LoginResult

interface ILoginUseCase {
    suspend operator fun invoke(args: LoginUCArgs): LoginResult
}