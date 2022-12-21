package com.example.data.repo

import com.example.data.db.dao.ArticleDao
import com.example.data.db.dao.ChatsDao
import com.example.data.db.dao.UserDao
import com.example.data.db.entities.UserEntity
import com.example.domain.models.ArticleData
import com.example.domain.models.ChatData
import com.example.domain.models.UserData
import com.example.domain.repo.IAvatarsRepo
import javax.inject.Inject

class AvatarsRepo @Inject constructor(
    private val userDao: UserDao,
    private val chatsDao: ChatsDao,
    private val articleDao: ArticleDao,
): IAvatarsRepo {
    override suspend fun updateUser(userData: UserData, avatarUrl: String) {
        val entity = userDao.getByName(userData.username)[0]
        val updated = entity.copy(avatarUrl = avatarUrl)
        userDao.updateUser(updated)
    }

    override suspend fun updateArticle(articleData: ArticleData, avatarUrl: String) {
        val author = userDao.getByName(articleData.author.username)[0]
        val entity = articleDao.getByTitleAndId(articleData.title, author.id)[0]
        val updated = entity.copy(imageUrl = avatarUrl)
        articleDao.updateArticle(updated)
    }

    override suspend fun updateChat(chatData: ChatData, avatarUrl: String) {
        val chat = chatsDao.getChatByTitle(chatData.title)[0]
        val updated = chat.copy(avatarUrl = avatarUrl)
        chatsDao.updateChat(updated)
    }
}