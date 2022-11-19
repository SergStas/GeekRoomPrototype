package com.example.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.models.ArticleData
import com.example.domain.models.UserData

@Entity(tableName = "articles")
data class ArticleEntity(
    val title: String,
    val imageUrl: String,
    val authorAvatarUrl: String,
    val content: String,
    val authorId: Long,
    val creationDate: Long,
    val commentsCount: Int = 0,
    val sharedCount: Int = 0,
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
) {
    companion object {
        fun ArticleData.toDbEntity(authorId: Long) =
            ArticleEntity(
                title = title,
                imageUrl = "",
                authorAvatarUrl = imageUrl,
                content = content,
                authorId = authorId,
                creationDate = creationDate,
                commentsCount = commentsCount,
                sharedCount = sharedCount,
            )
    }

    fun toDomainData(author: UserData, likedUsers: List<UserData>) =
        ArticleData(
            title = title,
            imageUrl = imageUrl,
            content = content,
            author = author,
            likedUsers = likedUsers,
            creationDate = creationDate,
            commentsCount = commentsCount,
            sharedCount = sharedCount,
        )
}
