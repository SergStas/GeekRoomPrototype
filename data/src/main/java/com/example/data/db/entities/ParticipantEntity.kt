package com.example.data.db.entities

import androidx.room.Entity

@Entity(
    tableName = "participants",
    primaryKeys = [ "userId", "chatId" ],
)
data class ParticipantEntity(
    val userId: Long,
    val chatId: Long,
)
