package com.example.domain.repo

import com.example.domain.models.ChatData
import com.example.domain.models.MessageData
import com.example.domain.models.UserData

interface IChatsRepo {
    suspend fun getUserChats(userData: UserData): List<ChatData>

    suspend fun getPopularChats(limit: Int = 10): List<ChatData>

    suspend fun createChat(user: UserData, participants: List<UserData>)

    suspend fun getMessagesForChat(chatData: ChatData): List<MessageData>

    suspend fun createMessage(chatData: ChatData, messageData: MessageData, sender: UserData)

    suspend fun readMessage(message: MessageData, user: UserData)
}