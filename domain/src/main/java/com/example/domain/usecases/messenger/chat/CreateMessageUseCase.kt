package com.example.domain.usecases.messenger.chat

import com.example.domain.models.ChatData
import com.example.domain.models.MessageData
import com.example.domain.repo.IAuthRepo
import com.example.domain.repo.IChatsRepo
import javax.inject.Inject

class CreateMessageUseCase @Inject constructor(
    private val chatsRepo: IChatsRepo,
    private val authRepo: IAuthRepo,
) {
    suspend operator fun invoke(chatData: ChatData, messageData: MessageData) =
        chatsRepo.createMessage(chatData, messageData, authRepo.getUser()!!)
}