package com.example.domain.usecases.login

import com.example.domain.repo.IAuthRepo
import com.example.domain.usecases.login.models.LoginArgs
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepo: IAuthRepo,
) {
    suspend operator fun invoke(args: LoginArgs) = authRepo.login(args.authData)
}