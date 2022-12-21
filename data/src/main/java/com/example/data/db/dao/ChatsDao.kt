package com.example.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.data.db.entities.ChatEntity
import com.example.data.db.entities.ParticipantEntity

@Dao
interface ChatsDao {
    @Query("select * from chats")
    suspend fun getAll(): List<ChatEntity>

    @Update
    suspend fun updateChat(chatEntity: ChatEntity)

    @Query("select * from participants where userId = :userId")
    suspend fun getUserParticipating(userId: Long): List<ParticipantEntity>

    @Query("select * from participants where chatId = :chatId")
    suspend fun getChatParticipants(chatId: Long): List<ParticipantEntity>

    @Insert
    suspend fun createChat(chat: ChatEntity): Long

    @Query("select * from chats where title = :title")
    suspend fun getChatByTitle(title: String): List<ChatEntity>

    @Insert
    suspend fun addParticipant(participant: ParticipantEntity)

    @Query("select chatId from (select chatId, count(*) as count " +
            "from participants group by chatId order by count desc) limit :limit")
    suspend fun getPopularChatIds(limit: Int): List<Long>

    @Query("select * from chats where id = :id")
    suspend fun getChatById(id: Long): List<ChatEntity>
}