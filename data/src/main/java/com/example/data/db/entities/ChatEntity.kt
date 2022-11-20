package com.example.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chats")
data class ChatEntity(
    val title: String,
    val avatarUrl: String,
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
)
