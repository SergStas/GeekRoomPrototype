package com.example.domain.repo

import com.example.domain.models.ChatData
import com.example.domain.models.UserData

interface IChatsRepo {
    suspend fun getUserChats(userData: UserData): List<ChatData>

    suspend fun getPopularChats(limit: Int = 10): List<ChatData>
}