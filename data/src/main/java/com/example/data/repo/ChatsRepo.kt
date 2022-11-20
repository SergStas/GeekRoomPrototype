package com.example.data.repo

import com.example.data.db.dao.ChatsDao
import com.example.data.db.dao.MessagesDao
import com.example.data.db.dao.UserDao
import com.example.data.db.entities.ChatEntity
import com.example.data.db.mapping.EntityMapper
import com.example.domain.models.UserData
import com.example.domain.repo.IChatsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChatsRepo @Inject constructor(
    private val chatsDao: ChatsDao,
    private val userDao: UserDao,
    private val mapper: EntityMapper,
): IChatsRepo {
    override suspend fun getUserChats(userData: UserData) =
        withContext(Dispatchers.IO) {
            val userId = userDao.getByName(userData.username)[0].id
            chatsDao.getUserParticipating(userId).map {
                mapper.mapChatEntity(chatsDao.getChatById(it.chatId)[0])
            }
        }

    override suspend fun getPopularChats(limit: Int) =
        withContext(Dispatchers.IO) {
            chatsDao.getPopularChatIds(limit).map {
                chatsDao.getChatById(it)[0]
            }.map { mapper.mapChatEntity(it) }
        }
}