package com.example.data.db.mapping

import com.example.data.db.dao.ChatsDao
import com.example.data.db.dao.LikesDao
import com.example.data.db.dao.MessagesDao
import com.example.data.db.dao.UserDao
import com.example.data.db.entities.ArticleEntity
import com.example.data.db.entities.ChatEntity
import com.example.data.db.entities.MessageEntity
import com.example.data.db.entities.UserEntity
import com.example.domain.models.ArticleData
import com.example.domain.models.ChatData
import com.example.domain.models.MessageData
import javax.inject.Inject

class EntityMapper @Inject constructor(
    private val userDao: UserDao,
    private val likesDao: LikesDao,
    private val chatsDao: ChatsDao,
    private val messageDao: MessagesDao,
) {
    fun mapUserEntity(entity: UserEntity) =
        entity.toUserData()

    suspend fun mapArticleEntity(entity: ArticleEntity): ArticleData {
        val author = userDao.getById(entity.authorId)[0].toUserData()
        val likes = likesDao.getLikesForArticle(entity.id).map {
            userDao.getById(it.userId)[0].toUserData()
        }
        return entity.toDomainData(
            author = author,
            likedUsers = likes,
        )
    }

    suspend fun mapChatEntity(entity: ChatEntity): ChatData {
        val participants = chatsDao.getChatParticipants(entity.id).map {
            userDao.getById(it.userId)[0]
        }
        val messages = messageDao.getMessagesForChat(entity.id).map {
            mapMessageEntity(it)
        }
        return ChatData(
            title = entity.title,
            avatarUrl = entity.avatarUrl,
            participants = participants.map { mapUserEntity(it) },
            messages = messages,
        )
    }

    suspend fun mapMessageEntity(entity: MessageEntity): MessageData {
        val sender = userDao.getById(entity.senderId)[0]
        val readUsers = messageDao.getMessageReadUsers(entity.id).map {
            val user = userDao.getById(it.userId)[0]
            mapUserEntity(user)
        }
        return MessageData(
            sender = mapUserEntity(sender),
            content = entity.content,
            sentTime = entity.sentTime,
            readUsers = readUsers,
        )
    }
}