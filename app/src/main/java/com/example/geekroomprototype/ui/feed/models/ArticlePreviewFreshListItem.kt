package com.example.geekroomprototype.ui.feed.models

import android.content.Context
import com.example.domain.models.ArticleData
import com.example.geekroomprototype.util.extensions.formatDate
import com.example.geekroomprototype.util.rv.IRvItem

data class ArticlePreviewFreshListItem(
    val title: String,
    val imageUrl: String,
    val content: String,
    val authorAvatarUrl: String,
    val authorName: String,
    val authorTag: String,
    val likesCount: Int,
    val creationDateToken: String,
    val onOpen: (ArticlePreviewFreshListItem) -> Unit,
    val commentsCount: Int = 0,
    val sharedCount: Int = 0,
    private val domain: ArticleData,
): IRvItem {
    companion object {
        fun fromDomain(
            article: ArticleData,
            context: Context,
            onOpen: (ArticlePreviewFreshListItem) -> Unit,
        ) = ArticlePreviewFreshListItem(
            title = article.title,
            imageUrl = article.imageUrl,
            content = article.content,
            authorAvatarUrl = article.author.avatarUrl,
            authorName = article.author.username,
            authorTag = article.author.username,
            likesCount = article.likedUsers.size,
            creationDateToken = formatDate(context, article.creationDate),
            onOpen = onOpen,
            domain = article,
        )
    }

    fun toDomain() = domain
}
