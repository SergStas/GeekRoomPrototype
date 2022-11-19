package com.example.domain.models

data class ArticleData(
    val title: String,
    val imageUrl: String,
    val content: String,
    val author: UserData,
    val likedUsers: List<UserData>,
    val creationDate: Long,
    val commentsCount: Int = 0,
    val sharedCount: Int = 0,
)
