package com.example.data.db.entities

import androidx.room.Entity

@Entity(
    tableName = "likes",
    primaryKeys = [ "userId", "articleId" ],
)
data class UserArticleLikeEntity(
    val userId: Long,
    val articleId: Long,
)
