package com.example.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.data.db.entities.MessageEntity
import com.example.data.db.entities.MessageReadEntity

@Dao
interface MessagesDao {
    @Query("select * from messages where chatId = :chatId order by sentTime desc")
    suspend fun getMessagesForChat(chatId: Long): List<MessageEntity>

    @Insert
    suspend fun create(message: MessageEntity): Long

    @Query("select * from read_messages where messageId = :messageId")
    suspend fun getMessageReadUsers(messageId: Long): List<MessageReadEntity>

    @Insert
    suspend fun readMessage(messageReadEntity: MessageReadEntity)
}