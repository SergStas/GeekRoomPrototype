package com.example.domain.repo

import com.example.domain.models.ArticleData
import com.example.domain.models.ChatData
import com.example.domain.models.UserData

interface IAvatarsRepo {
    suspend fun updateUser(userData: UserData, avatarUrl: String)
    suspend fun updateArticle(articleData: ArticleData, avatarUrl: String)
    suspend fun updateChat(chatData: ChatData, avatarUrl: String)
}