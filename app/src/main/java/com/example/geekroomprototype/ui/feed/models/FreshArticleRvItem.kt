package com.example.geekroomprototype.ui.feed.models

import com.example.geekroomprototype.util.rv.IRvItem

data class FreshArticleRvItem(
    val title: String,
    val imageUrl: String,
    val content: String,
    val authorAvatarUrl: String,
    val authorName: String,
    val authorTag: String,
    val likesCount: Int,
    val creationDateToken: String,
    val onOpen: (FreshArticleRvItem) -> Unit,
    val commentsCount: Int = 0,
    val sharedCount: Int = 0,
): IRvItem
