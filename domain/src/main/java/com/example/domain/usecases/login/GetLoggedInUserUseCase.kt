package com.example.domain.usecases.login

import com.example.domain.repo.IAuthRepo
import javax.inject.Inject

class GetLoggedInUserUseCase @Inject constructor(
    private val authRepo: IAuthRepo,
) {
    suspend operator fun invoke() = authRepo.getUser()
}