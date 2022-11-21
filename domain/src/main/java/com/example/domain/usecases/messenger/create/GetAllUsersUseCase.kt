package com.example.domain.usecases.messenger.create

import com.example.domain.repo.IAuthRepo
import com.example.domain.repo.IUsersRepo
import javax.inject.Inject

class GetAllUsersUseCase @Inject constructor(
    private val userRepo: IUsersRepo,
    private val authRepo: IAuthRepo,
) {
    suspend operator fun invoke() =
        userRepo.getAllUsers().toMutableList().apply {
            remove(authRepo.getUser()!!)
        }.toList()
}