package com.example.domain.usecases.feed.article

import com.example.domain.models.MessageData
import com.example.domain.repo.IAuthRepo
import com.example.domain.repo.IChatsRepo
import javax.inject.Inject

class MarkMessageAsReadUseCase @Inject constructor(
    private val authRepo: IAuthRepo,
    private val chatRepo: IChatsRepo,
) {
    suspend operator fun invoke(message: MessageData) {
        val user = authRepo.getUser()!!
        chatRepo.readMessage(message, user)
    }
}