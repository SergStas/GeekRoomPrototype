package com.example.domain.usecases.avatar

import com.example.domain.models.ChatData
import com.example.domain.repo.IAvatarsRepo
import javax.inject.Inject

class UpdateChatAvatarUseCase @Inject constructor(
    private val avatarsRepo: IAvatarsRepo,
) {
    suspend operator fun invoke(url: String, chatData: ChatData) =
        avatarsRepo.updateChat(chatData, url)
}