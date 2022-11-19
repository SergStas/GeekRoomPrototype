package com.example.domain.usecases.logout

import com.example.domain.repo.IAuthRepo
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val repo: IAuthRepo,
) {
    suspend operator fun invoke() = repo.logout()
}