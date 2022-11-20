package com.example.domain.usecases.messenger

import com.example.domain.repo.IAuthRepo
import com.example.domain.repo.IChatsRepo
import javax.inject.Inject

class QueryUserChatsUseCase @Inject constructor(
    private val authRepo: IAuthRepo,
    private val chatsRepo: IChatsRepo,
) {
    suspend operator fun invoke() =
        chatsRepo.getUserChats(authRepo.getUser()!!)
}