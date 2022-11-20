package com.example.data.db.entities

import androidx.room.Entity

@Entity(
    tableName = "read_messages",
    primaryKeys = [ "messageId", "userId" ],
)
data class MessageReadEntity(
    val messageId: Long,
    val userId: Long,
)
