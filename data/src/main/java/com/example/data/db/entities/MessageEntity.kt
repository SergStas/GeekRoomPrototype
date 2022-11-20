package com.example.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessageEntity(
    val chatId: Long,
    val senderId: Long,
    val content: String,
    val sentTime: Long,
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
)
