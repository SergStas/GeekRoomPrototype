package com.example.domain.usecases

import com.example.domain.repo.IAuthRepo
import com.example.domain.usecases.login.IGetLoggedInUserUseCase
import javax.inject.Inject

class GetLoggedInUserUseCase @Inject constructor(
    private val authRepo: IAuthRepo,
): IGetLoggedInUserUseCase {
    override suspend fun invoke() = authRepo.getUser()
}