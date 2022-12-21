package com.example.domain.usecases.avatar

import com.example.domain.repo.IAuthRepo
import com.example.domain.repo.IAvatarsRepo
import javax.inject.Inject

class UpdateUserAvatarUseCase @Inject constructor(
    private val avatarRepo: IAvatarsRepo,
    private val authRepo: IAuthRepo,
) {
    suspend operator fun invoke(url: String) {
        avatarRepo.updateUser(authRepo.getUser()!!, url)
    }
}