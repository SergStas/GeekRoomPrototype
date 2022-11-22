package com.example.domain.usecases.messenger.chat

import com.example.domain.models.ChatData
import com.example.domain.repo.IChatsRepo
import javax.inject.Inject

class GetMessagesForChatUseCase @Inject constructor(
    private val chatsRepo: IChatsRepo,
) {
    suspend operator fun invoke(chatData: ChatData) =
        chatsRepo.getMessagesForChat(chatData)
}