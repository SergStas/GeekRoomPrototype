package com.example.domain.usecases.login.models

import com.example.domain.repo.IAuthRepo
import com.example.domain.usecases.login.ILoginUseCase
import javax.inject.Inject

class LoginUseCaseImpl @Inject constructor(
    private val authRepo: IAuthRepo,
): ILoginUseCase {
    override suspend fun invoke(args: LoginUCArgs) =
        authRepo.login(args.authData)
}