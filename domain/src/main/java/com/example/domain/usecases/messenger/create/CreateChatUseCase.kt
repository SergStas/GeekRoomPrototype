package com.example.domain.usecases.messenger.create

import com.example.domain.models.UserData
import com.example.domain.repo.IAuthRepo
import com.example.domain.repo.IChatsRepo
import javax.inject.Inject

class CreateChatUseCase @Inject constructor(
    private val chatsRepo: IChatsRepo,
    private val authRepo: IAuthRepo,
) {
    suspend operator fun invoke(participants: List<UserData>) {
        val user = authRepo.getUser()!!
        val withoutUser = participants.toMutableList().apply { remove(user) }
        chatsRepo.createChat(user, withoutUser)
    }
}